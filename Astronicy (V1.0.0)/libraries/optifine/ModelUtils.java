package libraries.optifine;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;

public class ModelUtils
{
    public static void dbgModel(IBakedModel p_dbgModel_0_)
    {
        if (p_dbgModel_0_ != null)
        {
            Config.dbg("Model: " + p_dbgModel_0_ + ", ao: " + p_dbgModel_0_.isAmbientOcclusion() + ", gui3d: " + p_dbgModel_0_.isGui3d() + ", builtIn: " + p_dbgModel_0_.isBuiltInRenderer() + ", particle: " + p_dbgModel_0_.getParticleTexture());
            EnumFacing[] aenumfacing = EnumFacing.VALUES;

            for (int i = 0; i < aenumfacing.length; ++i)
            {
                EnumFacing enumfacing = aenumfacing[i];
                List list = p_dbgModel_0_.getFaceQuads(enumfacing);
                dbgQuads(enumfacing.getName(), list, "  ");
            }

            List list1 = p_dbgModel_0_.getGeneralQuads();
            dbgQuads("General", list1, "  ");
        }
    }

    private static void dbgQuads(String p_dbgQuads_0_, List<BakedQuad> p_dbgQuads_1_, String p_dbgQuads_2_)
    {
        for (BakedQuad bakedquad : p_dbgQuads_1_)
        {
            dbgQuad(p_dbgQuads_0_, bakedquad, p_dbgQuads_2_);
        }
    }

    public static void dbgQuad(String p_dbgQuad_0_, BakedQuad p_dbgQuad_1_, String p_dbgQuad_2_)
    {
        Config.dbg(p_dbgQuad_2_ + "Quad: " + p_dbgQuad_1_.getClass().getName() + ", type: " + p_dbgQuad_0_ + ", face: " + p_dbgQuad_1_.getFace() + ", tint: " + p_dbgQuad_1_.getTintIndex() + ", sprite: " + p_dbgQuad_1_.getSprite());
        dbgVertexData(p_dbgQuad_1_.getVertexData(), "  " + p_dbgQuad_2_);
    }

    public static void dbgVertexData(int[] p_dbgVertexData_0_, String p_dbgVertexData_1_)
    {
        int i = p_dbgVertexData_0_.length / 4;
        Config.dbg(p_dbgVertexData_1_ + "Length: " + p_dbgVertexData_0_.length + ", step: " + i);

        for (int j = 0; j < 4; ++j)
        {
            int k = j * i;
            float f = Float.intBitsToFloat(p_dbgVertexData_0_[k + 0]);
            float f1 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 1]);
            float f2 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 2]);
            int l = p_dbgVertexData_0_[k + 3];
            float f3 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 4]);
            float f4 = Float.intBitsToFloat(p_dbgVertexData_0_[k + 5]);
            Config.dbg(p_dbgVertexData_1_ + j + " xyz: " + f + "," + f1 + "," + f2 + " col: " + l + " u,v: " + f3 + "," + f4);
        }
    }

    public static IBakedModel duplicateModel(IBakedModel p_duplicateModel_0_)
    {
        List list = duplicateQuadList(p_duplicateModel_0_.getGeneralQuads());
        EnumFacing[] aenumfacing = EnumFacing.VALUES;
        List list1 = new ArrayList();

        for (int i = 0; i < aenumfacing.length; ++i)
        {
            EnumFacing enumfacing = aenumfacing[i];
            List list2 = p_duplicateModel_0_.getFaceQuads(enumfacing);
            List list3 = duplicateQuadList(list2);
            list1.add(list3);
        }

        SimpleBakedModel simplebakedmodel = new SimpleBakedModel(list, list1, p_duplicateModel_0_.isAmbientOcclusion(), p_duplicateModel_0_.isGui3d(), p_duplicateModel_0_.getParticleTexture(), p_duplicateModel_0_.getItemCameraTransforms());
        return simplebakedmodel;
    }

    public static List<BakedQuad> duplicateQuadList(List<BakedQuad> p_duplicateQuadList_0_)
    {
        List<BakedQuad> list = new ArrayList<BakedQuad>();

        for (BakedQuad bakedquad : p_duplicateQuadList_0_)
        {
            BakedQuad bakedquad1 = duplicateQuad(bakedquad);
            list.add(bakedquad1);
        }

        return list;
    }

    public static BakedQuad duplicateQuad(BakedQuad p_duplicateQuad_0_)
    {
        BakedQuad bakedquad = new BakedQuad((int[])p_duplicateQuad_0_.getVertexData().clone(), p_duplicateQuad_0_.getTintIndex(), p_duplicateQuad_0_.getFace(), p_duplicateQuad_0_.getSprite());
        return bakedquad;
    }
}
