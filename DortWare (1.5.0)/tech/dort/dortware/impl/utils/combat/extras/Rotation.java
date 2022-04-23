package tech.dort.dortware.impl.utils.combat.extras;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import tech.dort.dortware.impl.utils.combat.AimUtil;

public final class Rotation {

    private final float rotationYaw;

    private final float rotationPitch;

    /**
     * Constructs a {@code Rotation} instance.
     *
     * @param rotationYaw   - The yaw
     * @param rotationPitch - The pitch
     */
    public Rotation(float rotationYaw, float rotationPitch) {
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
    }

    public static Rotation fromArray(float[] array) {
        return new Rotation(array[0], array[1]);
    }

    public static Rotation fromFacing(EntityLivingBase entity) {
        Rotation rotation = AimUtil.getRotationsRandom(entity);
        return new Rotation(MathHelper.floor_double(rotation.getRotationYaw()), rotation.getRotationPitch());
    }

    public float getRotationYaw() {
        return rotationYaw;
    }

    public float getRotationPitch() {
        return rotationPitch;
    }
}
