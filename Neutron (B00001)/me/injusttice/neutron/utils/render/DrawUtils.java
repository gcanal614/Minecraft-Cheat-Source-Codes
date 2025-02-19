package me.injusttice.neutron.utils.render;

import me.injusttice.neutron.utils.font.StaticallySizedImage;
import me.injusttice.neutron.utils.pathfinding.ResourceUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.opengl.GL20.*;

public final class DrawUtils {

    private static final FloatBuffer WND_POS_BUFFER = GLAllocation.createDirectFloatBuffer(4);
    private static final IntBuffer VIEWPORT_BUFFER = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer MODEL_MATRIX_BUFFER = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer PROJECTION_MATRIX_BUFFER = GLAllocation.createDirectFloatBuffer(16);
    private static final IntBuffer SCISSOR_BUFFER = GLAllocation.createDirectIntBuffer(16);


    public static double animateProgress(final double current, final double target, final double speed) {
        if (current < target) {
            final double inc = 1.0 / Minecraft.getDebugFPS() * speed;
            if (target - current < inc) {
                return target;
            } else {
                return current + inc;
            }
        } else if (current > target) {
            final double inc = 1.0 / Minecraft.getDebugFPS() * speed;
            if (current - target < inc) {
                return target;
            } else {
                return current - inc;
            }
        }

        return current;
    }

    public static double bezierBlendAnimation(double t) {
        return t * t * (3.0 - 2.0 * t);
    }

    public static void glDrawTriangle(final double x, final double y,
                                      final double x1, final double y1,
                                      final double x2, final double y2,
                                      final int colour) {
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Enable blending
        final boolean restore = glEnableBlend();
        // Enable anti-aliasing
        glEnable(GL_POLYGON_SMOOTH);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);

        glColour(colour);

        // Start drawing a triangle
        glBegin(GL_TRIANGLES);
        {
            glVertex2d(x, y);
            glVertex2d(x1, y1);
            glVertex2d(x2, y2);
        }
        glEnd();

        // Enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blending
        glRestoreBlend(restore);
        // Disable anti-aliasing
        glDisable(GL_POLYGON_SMOOTH);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_DONT_CARE);
    }

    public static void glDrawPlusSign(final double x,
                                      final double y,
                                      final double size,
                                      final double rotation,
                                      final int colour) {
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Enable blending
        final boolean restore = glEnableBlend();
        // Enable anti-aliasing
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        // Set line width
        glLineWidth(1.f);
        // Push new matrix
        glPushMatrix();
        // Translate matrix
        glTranslated(x, y, 0);
        // Rotate matrix by rotation value (do after translation
        glRotated(rotation, 0, 1, 1);
        // Disable depth
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);

        glColour(colour);

        // Start drawing a triangle
        glBegin(GL_LINES);
        {
            // Horizontal stroke
            glVertex2d(-(size / 2.0), 0);
            glVertex2d(size / 2.0, 0);
            // Vertical stroke
            glVertex2d(0, -(size / 2.0));
            glVertex2d(0, size / 2.0);
        }
        glEnd();

        // Enable depth
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        // Pop off old matrix (restore)
        glPopMatrix();
        // Enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blending
        glRestoreBlend(restore);
        // Disable anti-aliasing
        glDisable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
    }

    public static void glDrawFilledEllipse(final double x,
                                           final double y,
                                           final double radius,
                                           final int startIndex,
                                           final int endIndex,
                                           final int polygons,
                                           final boolean smooth,
                                           final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();

        if (smooth) {
            // Enable anti-aliasing
            glEnable(GL_POLYGON_SMOOTH);
            glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        }
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);
        // Required because of minecraft optimizations
        glDisable(GL_CULL_FACE);

        // Begin triangle fan
        glBegin(GL_POLYGON);
        {
            // Specify center vertex
            glVertex2d(x, y);

            for (double i = startIndex; i <= endIndex; i++) {
                final double theta = 2.0 * Math.PI * i / polygons;
                // Specify triangle fan vertices in a circle (size=radius) around x & y
                glVertex2d(x + radius * Math.cos(theta), y + radius * Math.sin(theta));
            }
        }
        // Draw the triangle fan
        glEnd();

        // Disable blending
        glRestoreBlend(restore);

        if (smooth) {
            // Disable anti-aliasing
            glDisable(GL_POLYGON_SMOOTH);
            glHint(GL_POLYGON_SMOOTH_HINT, GL_DONT_CARE);
        }
        // See above
        glEnable(GL_CULL_FACE);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawFilledEllipse(final double x,
                                           final double y,
                                           final float radius,
                                           final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Enable anti-aliasing
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);
        // See the point size aka radius
        glPointSize(radius);

        glBegin(GL_POINTS);
        {
            glVertex2d(x, y);
        }
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Disable anti-aliasing
        glDisable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_DONT_CARE);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glScissorBox(final double x, final double y,
                                    final double width, final double height,
                                    final ScaledResolution scaledResolution) {
        if (!glIsEnabled(GL_SCISSOR_TEST))
            glEnable(GL_SCISSOR_TEST);

        final int scaling = scaledResolution.getScaleFactor();

        glScissor((int) (x * scaling),
                (int) ((scaledResolution.getScaledHeight() - (y + height)) * scaling),
                (int) (width * scaling),
                (int) (height * scaling));
    }

    public static void glRestoreScissor() {
        if (!glIsEnabled(GL_SCISSOR_TEST))
            glEnable(GL_SCISSOR_TEST);

        // Restore the last saved scissor box
        glScissor(SCISSOR_BUFFER.get(0), SCISSOR_BUFFER.get(1),
                SCISSOR_BUFFER.get(2), SCISSOR_BUFFER.get(3));
    }

    public static void glEndScissor() {
        glDisable(GL_SCISSOR_TEST);
    }

    public static double[] worldToScreen(final double[] positionVector,
                                         final AxisAlignedBB boundingBox,
                                         final double[] projection,
                                         final double[] projectionBuffer) {
        final double[][] bounds = {
                {boundingBox.minX, boundingBox.minY, boundingBox.minZ},
                {boundingBox.minX, boundingBox.maxY, boundingBox.minZ},
                {boundingBox.minX, boundingBox.maxY, boundingBox.maxZ},
                {boundingBox.minX, boundingBox.minY, boundingBox.maxZ},
                {boundingBox.maxX, boundingBox.minY, boundingBox.minZ},
                {boundingBox.maxX, boundingBox.maxY, boundingBox.minZ},
                {boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ},
                {boundingBox.maxX, boundingBox.minY, boundingBox.maxZ}
        };

        final double[] position;

        // null when chests (don't need pos vector proj. for chests)
        if (positionVector != null) {
            if (!worldToScreen(positionVector, projectionBuffer, projection[2]))
                return null;

            position = new double[]{
                    projection[0], projection[1], // screen max width/height
                    -1.f, -1.f, // negative placeholder values for > comparison
                    projectionBuffer[0], projectionBuffer[1] // player position vector x/y
            };
        } else {
            position = new double[]{
                    projection[0], projection[1], // screen max width/height
                    -1.f, -1.f, // negative placeholder values for > comparison
            };
        }

        for (final double[] vector : bounds) {
            if (worldToScreen(vector, projectionBuffer, projection[2])) {
                final double projected_x = projectionBuffer[0];
                final double projected_y = projectionBuffer[1];

                position[0] = Math.min(position[0], projected_x);
                position[1] = Math.min(position[1], projected_y);
                position[2] = Math.max(position[2], projected_x);
                position[3] = Math.max(position[3], projected_y);
            }
        }

        return position;
    }

    public static boolean worldToScreen(double[] in, double[] out, double scaling) {
        glGetFloat(GL_MODELVIEW_MATRIX, MODEL_MATRIX_BUFFER);
        glGetFloat(GL_PROJECTION_MATRIX, PROJECTION_MATRIX_BUFFER);
        glGetInteger(GL_VIEWPORT, VIEWPORT_BUFFER);

        if (GLU.gluProject((float) in[0], (float) in[1], (float) in[2],
                MODEL_MATRIX_BUFFER, PROJECTION_MATRIX_BUFFER,
                VIEWPORT_BUFFER, WND_POS_BUFFER)) {
            final float zCoordinate = WND_POS_BUFFER.get(2);
            // Check z coordinate is within bounds 0-<1.0
            if (zCoordinate < 0.0F || zCoordinate > 1.0F) return false;

            out[0] = WND_POS_BUFFER.get(0) / scaling; // window pos (x) / scaled resolution scale (normal = 2)
            // GL handles the 'y' window coordinate inverted to Minecraft
            // subtract window pos y from bottom of screen and divide by scaled res scale
            out[1] = (Display.getHeight() - WND_POS_BUFFER.get(1)) / scaling;
            return true;
        }

        return false;
    }

    public static void glColour(final int color) {
        glColor4ub((byte) (color >> 16 & 0xFF),
                (byte) (color >> 8 & 0xFF),
                (byte) (color & 0xFF),
                (byte) (color >> 24 & 0xFF));
    }

    public static void glDrawLine(final double x,
                                  final double y,
                                  final double x1,
                                  final double y1,
                                  final float lineWidth,
                                  final boolean smoothed,
                                  final int colour) {
        // Enable blending (required for anti-aliasing)
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set line width
        glLineWidth(lineWidth);

        if (smoothed) {
            // Enable line anti-aliasing
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        }

        glColour(colour);

        // Begin line
        glBegin(GL_LINES);
        {
            // Start
            glVertex2d(x, y);
            // End
            glVertex2d(x1, y1);
        }
        // Draw the line
        glEnd();

        // Restore blend
        glRestoreBlend(restore);
        if (smoothed) {
            // Disable line anti-aliasing
            glDisable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        }
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawPlayerFace(final double x,
                                        final double y,
                                        final double width,
                                        final double height,
                                        final ResourceLocation skinLocation) {
        // Bind skin texture
        Minecraft.getMinecraft().getTextureManager().bindTexture(skinLocation);
        // Colour solid
        glColor4f(1, 1, 1, 1);
        final float eightPixelOff = 1.0F / 8;

        glBegin(GL_QUADS);
        {
            glTexCoord2f(eightPixelOff, eightPixelOff);
            glVertex2d(x, y);

            glTexCoord2f(eightPixelOff, eightPixelOff * 2);
            glVertex2d(x, y + height);

            glTexCoord2f(eightPixelOff * 2, eightPixelOff * 2);
            glVertex2d(x + width, y + height);

            glTexCoord2f(eightPixelOff * 2, eightPixelOff);
            glVertex2d(x + width, y);
        }
        glEnd();
    }

    public static void glDrawSidewaysGradientRect(final double x,
                                                  final double y,
                                                  final double width,
                                                  final double height,
                                                  final int startColour,
                                                  final int endColour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Enable vertex colour changing
        glShadeModel(GL_SMOOTH);

        // Begin rect
        glBegin(GL_QUADS);
        {
            // Start fade
            glColour(startColour);
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            // End fade
            glColour(endColour);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        // Restore shade model
        glShadeModel(GL_FLAT);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blending
        glRestoreBlend(restore);
    }

    public static void glDrawFilledRect(final double x,
                                        final double y,
                                        final double x1,
                                        final double y1,
                                        final int startColour,
                                        final int endColour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Enable vertex colour changing
        glShadeModel(GL_SMOOTH);

        // Begin rect
        glBegin(GL_QUADS);
        {
            // Start fade
            glColour(startColour);
            glVertex2d(x, y);
            glColour(endColour);
            glVertex2d(x, y1);
            // End fade
            glVertex2d(x1, y1);
            glColour(startColour);
            glVertex2d(x1, y);
        }
        // Draw the rect
        glEnd();

        // Restore shade model
        glShadeModel(GL_FLAT);

        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blending
        glRestoreBlend(restore);
    }

    public static void glDrawOutlinedQuad(final double x,
                                          final double y,
                                          final double width,
                                          final double height,
                                          final float thickness,
                                          final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);

        glLineWidth(thickness);

        // Begin rect
        glBegin(GL_LINE_LOOP);
        {
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blend
        glRestoreBlend(restore);
    }

    public static void drawHollowRoundedRect(double x,
                                             double y,
                                             double width,
                                             double height,
                                             double cornerRadius,
                                             boolean smoothed,
                                             Color color) {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glEnable(GL_BLEND);
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255.0F, color.getAlpha() / 255F);
        glLineWidth(1.0f);
        glBegin(GL_LINE_LOOP);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        for (int i = 0; i <= 90; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        glBegin(GL_LINE_LOOP);
        for (int i = 90; i <= 180; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        glBegin(GL_LINE_LOOP);
        for (int i = 180; i <= 270; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        glBegin(GL_LINE_LOOP);
        for (int i = 270; i <= 360; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDrawLine(x + cornerRadius, y, x + width - cornerRadius, y, 1.0f, smoothed, color.getRGB());
        glDrawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height, 1.0f, smoothed, color.getRGB());
        glDrawLine(x, y + cornerRadius, x, y + height - cornerRadius, 1.0f, smoothed, color.getRGB());
        glDrawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius, 1.0f, smoothed, color.getRGB());
    }

    public static void glDrawOutlinedQuadGradient(final double x,
                                                  final double y,
                                                  final double width,
                                                  final double height,
                                                  final float thickness,
                                                  final int colour, final int secondaryColour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);

        glLineWidth(thickness);

        // Begin rect
        glShadeModel(GL_SMOOTH);
        glBegin(GL_LINE_LOOP);
        {
            // Set color
            glColour(colour);
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            // Set second color
            glColour(secondaryColour);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();
        glShadeModel(GL_FLAT);

        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blend
        glRestoreBlend(restore);
    }

    private static StaticallySizedImage checkmarkImage;
    private static StaticallySizedImage warningImage;

    public static void glDrawCheckmarkImage(final double x,
                                            final double y,
                                            final double width,
                                            final double height,
                                            final int colour) {
        checkmarkImage.draw(x, y, width, height, colour);
    }

    public static void glDrawWarningImage(final double x,
                                          final double y,
                                          final double width,
                                          final double height,
                                          final int colour) {
        warningImage.draw(x, y, width, height, colour);
    }

    public static void glDrawFilledQuad(final double x,
                                        final double y,
                                        final double width,
                                        final double height,
                                        final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);

        // Begin rect
        glBegin(GL_QUADS);
        {
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawFilledQuad(final double x,
                                        final double y,
                                        final double width,
                                        final double height,
                                        final int startColour,
                                        final int endColour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);

        glShadeModel(GL_SMOOTH);

        // Begin rect
        glBegin(GL_QUADS);
        {
            glColour(startColour);
            glVertex2d(x, y);

            glColour(endColour);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);

            glColour(startColour);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        glShadeModel(GL_FLAT);

        // Disable blending
        glRestoreBlend(restore);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawFilledRect(final double x,
                                        final double y,
                                        final double x1,
                                        final double y1,
                                        final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);

        // Begin rect
        glBegin(GL_QUADS);
        {
            glVertex2d(x, y);
            glVertex2d(x, y1);
            glVertex2d(x1, y1);
            glVertex2d(x1, y);
        }
        // Draw the rect
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawArcOutline(final double x,
                                        final double y,
                                        final float radius,
                                        final float angleStart,
                                        final float angleEnd,
                                        final float lineWidth,
                                        final int colour) {
        // Derive segments from size
        final int segments = (int) (radius * 4);
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set the width of the line
        glLineWidth(lineWidth);
        // Set color
        glColour(colour);
        // Enable triangle smoothing
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        // Translate to centre of arc
        glTranslated(x, y, 0);
        // Begin triangle fan
        glBegin(GL_LINE_STRIP);
        {
            final float[][] vertices = MathUtil.getArcVertices(radius, angleStart, angleEnd, segments);

            for (float[] vertex : vertices) {
                // Specify triangle fan vertices in a circle (size=radius) around x & y
                glVertex2f(vertex[0], vertex[1]);
            }
        }
        // Draw the triangle fan
        glEnd();
        // Restore matrix
        glTranslated(-x, -y, 0);
        // Disable triangle smoothing
        glDisable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        // Disable blending
        glRestoreBlend(restore);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }


    public static void glDrawPoint(final double x,
                                   final double y,
                                   final float radius,
                                   final ScaledResolution scaledResolution,
                                   final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Enable anti-aliasing
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);
        // See the point size aka radius
        glPointSize(radius * glGetFloat(GL_MODELVIEW_MATRIX) * scaledResolution.getScaleFactor());

        glBegin(GL_POINTS);
        {
            glVertex2d(x, y);
        }
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Disable anti-aliasing
        glDisable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_DONT_CARE);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawRoundedOutline(final double x,
                                            final double y,
                                            final double width,
                                            final double height,
                                            final float lineWidth,
                                            final RoundingMode roundingMode,
                                            final float rounding,
                                            final int colour) {
        boolean bLeft = false;
        boolean tLeft = false;
        boolean bRight = false;
        boolean tRight = false;

        switch (roundingMode) {
            case TOP:
                tLeft = true;
                tRight = true;
                break;
            case BOTTOM:
                bLeft = true;
                bRight = true;
                break;
            case FULL:
                tLeft = true;
                tRight = true;
                bLeft = true;
                bRight = true;
                break;
            case LEFT:
                bLeft = true;
                tLeft = true;
                break;
            case RIGHT:
                bRight = true;
                tRight = true;
                break;
            case TOP_LEFT:
                tLeft = true;
                break;
            case TOP_RIGHT:
                tRight = true;
                break;
            case BOTTOM_LEFT:
                bLeft = true;
                break;
            case BOTTOM_RIGHT:
                bRight = true;
                break;
        }

        // Translate matrix to top-left of rect
        glTranslated(x, y, 0);
        // Enable blending
        final boolean restore = glEnableBlend();

        if (tLeft) {
            // Top left
            glDrawArcOutline(rounding, rounding, rounding,
                    270.f, 360.f, lineWidth, colour);
        }

        if (tRight) {
            // Top right
            glDrawArcOutline(width - rounding, rounding, rounding,
                    0.f, 90.f, lineWidth, colour);
        }

        if (bLeft) {
            // Bottom left
            glDrawArcOutline(rounding, height - rounding, rounding,
                    180, 270, lineWidth, colour);
        }

        if (bRight) {
            // Bottom right
            glDrawArcOutline(width - rounding, height - rounding, rounding,
                    90, 180, lineWidth, colour);
        }

        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set colour
        glColour(colour);

        // Begin polygon
        glBegin(GL_LINES);
        {
            if (tLeft) {
                glVertex2d(0.0, rounding);
            } else {
                glVertex2d(0.0, 0.0);
            }

            if (bLeft) {
                glVertex2d(0, height - rounding);
                glVertex2d(rounding, height);
            } else {
                glVertex2d(0.0, height);
                glVertex2d(0.0, height);
            }

            if (bRight) {
                glVertex2d(width - rounding, height);
                glVertex2d(width, height - rounding);
            } else {
                glVertex2d(width, height);
                glVertex2d(width, height);
            }

            if (tRight) {
                glVertex2d(width, rounding);
                glVertex2d(width - rounding, 0.0);
            } else {
                glVertex2d(width, 0.0);
                glVertex2d(width, 0.0);
            }

            if (tLeft) {
                glVertex2d(rounding, 0.0);
            } else {
                glVertex2d(0.0, 0.0);
            }
        }
        // Draw polygon
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Translate matrix back (instead of creating a new matrix with glPush/glPop)
        glTranslated(-x, -y, 0);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawRoundedRect(final double x,
                                         final double y,
                                         final double width,
                                         final double height,
                                         final RoundingMode roundingMode,
                                         final float rounding,
                                         final float scaleFactor,
                                         final int colour) {
        boolean bLeft = false;
        boolean tLeft = false;
        boolean bRight = false;
        boolean tRight = false;

        switch (roundingMode) {
            case TOP:
                tLeft = true;
                tRight = true;
                break;
            case BOTTOM:
                bLeft = true;
                bRight = true;
                break;
            case FULL:
                tLeft = true;
                tRight = true;
                bLeft = true;
                bRight = true;
                break;
            case LEFT:
                bLeft = true;
                tLeft = true;
                break;
            case RIGHT:
                bRight = true;
                tRight = true;
                break;
            case TOP_LEFT:
                tLeft = true;
                break;
            case TOP_RIGHT:
                tRight = true;
                break;
            case BOTTOM_LEFT:
                bLeft = true;
                break;
            case BOTTOM_RIGHT:
                bRight = true;
                break;
        }

        final float alpha = (colour >> 24 & 0xFF) / 255.f;

        // Enable blending
        final boolean restore = glEnableBlend();

        // Set colour
        DrawUtil.glColour(colour);

        // Translate matrix to top-left of rect
        glTranslated(x, y, 0);
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);

        // Begin polygon
        glBegin(GL_POLYGON);
        {
            if (tLeft) {
                glVertex2d(rounding, rounding);
                glVertex2d(0, rounding);
            } else {
                glVertex2d(0, 0);
            }

            if (bLeft) {
                glVertex2d(0, height - rounding);
                glVertex2d(rounding, height - rounding);
                glVertex2d(rounding, height);
            } else {
                glVertex2d(0, height);
            }

            if (bRight) {
                glVertex2d(width - rounding, height);
                glVertex2d(width - rounding, height - rounding);
                glVertex2d(width, height - rounding);
            } else {
                glVertex2d(width, height);
            }

            if (tRight) {
                glVertex2d(width, rounding);
                glVertex2d(width - rounding, rounding);
                glVertex2d(width - rounding, 0);
            } else {
                glVertex2d(width, 0);
            }

            if (tLeft) {
                glVertex2d(rounding, 0);
            }
        }
        // Draw polygon
        glEnd();

        // Enable anti-aliasing
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

        // Set point size
        glPointSize(rounding * 2.f * glGetFloat(GL_MODELVIEW_MATRIX) * scaleFactor);

        glBegin(GL_POINTS);
        {
            if (tLeft) {
                // Top left
                glVertex2d(rounding, rounding);
            }

            if (tRight) {
                // Top right
                glVertex2d(width - rounding, rounding);
            }

            if (bLeft) {
                // Bottom left
                glVertex2d(rounding, height - rounding);
            }

            if (bRight) {
                // Bottom right
                glVertex2d(width - rounding, height - rounding);
            }
        }
        glEnd();

        // Disable anti-aliasing
        glDisable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_DONT_CARE);
        // Disable blending
        glRestoreBlend(restore);
        // Translate matrix back (instead of creating a new matrix with glPush/glPop)
        glTranslated(-x, -y, 0);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }


    public static void glDrawRoundedRectEllipse(final double x,
                                                final double y,
                                                final double width,
                                                final double height,
                                                final RoundingMode roundingMode,
                                                final int roundingDef,
                                                final double roundingLevel,
                                                final int colour) {
        boolean bLeft = false;
        boolean tLeft = false;
        boolean bRight = false;
        boolean tRight = false;

        switch (roundingMode) {
            case TOP:
                tLeft = true;
                tRight = true;
                break;
            case BOTTOM:
                bLeft = true;
                bRight = true;
                break;
            case FULL:
                tLeft = true;
                tRight = true;
                bLeft = true;
                bRight = true;
                break;
            case LEFT:
                bLeft = true;
                tLeft = true;
                break;
            case RIGHT:
                bRight = true;
                tRight = true;
                break;
            case TOP_LEFT:
                tLeft = true;
                break;
            case TOP_RIGHT:
                tRight = true;
                break;
            case BOTTOM_LEFT:
                bLeft = true;
                break;
            case BOTTOM_RIGHT:
                bRight = true;
                break;
        }

        // Translate matrix to top-left of rect
        glTranslated(x, y, 0);
        // Enable triangle anti-aliasing
        glEnable(GL_POLYGON_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        // Enable blending
        final boolean restore = glEnableBlend();

        if (tLeft) {
            // Top left
            glDrawFilledEllipse(roundingLevel, roundingLevel, roundingLevel,
                    (int) (roundingDef * 0.5), (int) (roundingDef * 0.75),
                    roundingDef, false, colour);
        }

        if (tRight) {
            // Top right
            glDrawFilledEllipse(width - roundingLevel, roundingLevel, roundingLevel,
                    (int) (roundingDef * 0.75), roundingDef,
                    roundingDef, false, colour);
        }

        if (bLeft) {
            // Bottom left
            glDrawFilledEllipse(roundingLevel, height - roundingLevel, roundingLevel,
                    (int) (roundingDef * 0.25), (int) (roundingDef * 0.5),
                    roundingDef, false, colour);
        }

        if (bRight) {
            // Bottom right
            glDrawFilledEllipse(width - roundingLevel, height - roundingLevel, roundingLevel,
                    0, (int) (roundingDef * 0.25),
                    roundingDef, false, colour);
        }

        // Enable triangle anti-aliasing (to save performance on next poly draw)
        glDisable(GL_POLYGON_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);

        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set colour
        glColour(colour);

        // Begin polygon
        glBegin(GL_POLYGON);
        {
            if (tLeft) {
                glVertex2d(roundingLevel, roundingLevel);
                glVertex2d(0, roundingLevel);
            } else {
                glVertex2d(0, 0);
            }

            if (bLeft) {
                glVertex2d(0, height - roundingLevel);
                glVertex2d(roundingLevel, height - roundingLevel);
                glVertex2d(roundingLevel, height);
            } else {
                glVertex2d(0, height);
            }

            if (bRight) {
                glVertex2d(width - roundingLevel, height);
                glVertex2d(width - roundingLevel, height - roundingLevel);
                glVertex2d(width, height - roundingLevel);
            } else {
                glVertex2d(width, height);
            }

            if (tRight) {
                glVertex2d(width, roundingLevel);
                glVertex2d(width - roundingLevel, roundingLevel);
                glVertex2d(width - roundingLevel, 0);
            } else {
                glVertex2d(width, 0);
            }

            if (tLeft) {
                glVertex2d(roundingLevel, 0);
            }
        }
        // Draw polygon
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Translate matrix back (instead of creating a new matrix with glPush/glPop)
        glTranslated(-x, -y, 0);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static boolean glEnableBlend() {
        final boolean wasEnabled = glIsEnabled(GL_BLEND);

        if (!wasEnabled) {
            glEnable(GL_BLEND);
            glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        }

        return wasEnabled;
    }

    public static void glRestoreBlend(final boolean wasEnabled) {
        if (!wasEnabled) {
            glDisable(GL_BLEND);
        }
    }

    public static float interpolate(final float old, final float now, final float progress) {
        return old + (now - old) * progress;
    }

    public static double interpolate(final double old, final double now, final double progress) {
        return old + (now - old) * progress;
    }

    public static Vec3 interpolate(final Vec3 old, final Vec3 now, final double progress) {
        final Vec3 difVec = now.subtract(old);
        return new Vec3(old.xCoord + difVec.xCoord * progress,
                old.yCoord + difVec.yCoord * progress,
                old.zCoord + difVec.zCoord * progress);
    }

    public static double[] interpolate(final Entity entity, final float partialTicks) {
        return new double[]{
                interpolate(entity.prevPosX, entity.posX, partialTicks),
                interpolate(entity.prevPosY, entity.posY, partialTicks),
                interpolate(entity.prevPosZ, entity.posZ, partialTicks),
        };
    }

    public static AxisAlignedBB interpolate(final Entity entity,
                                            final AxisAlignedBB boundingBox,
                                            final float partialTicks) {
        final float invertedPT = 1.0f - partialTicks;
        return boundingBox.offset(
                (entity.posX - entity.prevPosX) * -invertedPT,
                (entity.posY - entity.prevPosY) * -invertedPT,
                (entity.posZ - entity.prevPosZ) * -invertedPT
        );
    }

    public static void glDrawBoundingBox(final AxisAlignedBB bb,
                                         final float lineWidth,
                                         final boolean filled) {
        if (filled) {
            // 4 sides
            glBegin(GL_QUAD_STRIP);
            {
                glVertex3d(bb.minX, bb.minY, bb.minZ);
                glVertex3d(bb.minX, bb.maxY, bb.minZ);

                glVertex3d(bb.maxX, bb.minY, bb.minZ);
                glVertex3d(bb.maxX, bb.maxY, bb.minZ);

                glVertex3d(bb.maxX, bb.minY, bb.maxZ);
                glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

                glVertex3d(bb.minX, bb.minY, bb.maxZ);
                glVertex3d(bb.minX, bb.maxY, bb.maxZ);

                glVertex3d(bb.minX, bb.minY, bb.minZ);
                glVertex3d(bb.minX, bb.maxY, bb.minZ);
            }
            glEnd();

            // Bottom
            glBegin(GL_QUADS);
            {
                glVertex3d(bb.minX, bb.minY, bb.minZ);
                glVertex3d(bb.maxX, bb.minY, bb.minZ);
                glVertex3d(bb.maxX, bb.minY, bb.maxZ);
                glVertex3d(bb.minX, bb.minY, bb.maxZ);
            }
            glEnd();

            glCullFace(GL_FRONT);

            // Top
            glBegin(GL_QUADS);
            {
                glVertex3d(bb.minX, bb.maxY, bb.minZ);
                glVertex3d(bb.maxX, bb.maxY, bb.minZ);
                glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
                glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            }
            glEnd();

            glCullFace(GL_BACK);
        }


        if (lineWidth > 0) {
            glLineWidth(lineWidth);

            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);

            glBegin(GL_LINE_STRIP);
            {
                // Bottom
                glVertex3d(bb.minX, bb.minY, bb.minZ);
                glVertex3d(bb.maxX, bb.minY, bb.minZ);
                glVertex3d(bb.maxX, bb.minY, bb.maxZ);
                glVertex3d(bb.minX, bb.minY, bb.maxZ);
                glVertex3d(bb.minX, bb.minY, bb.minZ);

                // Top
                glVertex3d(bb.minX, bb.maxY, bb.minZ);
                glVertex3d(bb.maxX, bb.maxY, bb.minZ);
                glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
                glVertex3d(bb.minX, bb.maxY, bb.maxZ);
                glVertex3d(bb.minX, bb.maxY, bb.minZ);
            }
            glEnd();

            glBegin(GL_LINES);
            {
                glVertex3d(bb.maxX, bb.minY, bb.minZ);
                glVertex3d(bb.maxX, bb.maxY, bb.minZ);

                glVertex3d(bb.maxX, bb.minY, bb.maxZ);
                glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

                glVertex3d(bb.minX, bb.minY, bb.maxZ);
                glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            }
            glEnd();

            glDisable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        }
    }

    public enum RoundingMode {
        TOP_LEFT,
        BOTTOM_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,

        LEFT,
        RIGHT,

        TOP,
        BOTTOM,

        FULL
    }
}
