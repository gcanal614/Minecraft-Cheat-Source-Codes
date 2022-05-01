package net.minecraft.client.resources.model;

import cn.Arctic.Client;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener
{
    private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;
    private final TextureMap texMap;
    private final BlockModelShapes modelProvider;
    private IBakedModel defaultModel;

    public ModelManager(TextureMap textures)
    {
        this.texMap = textures;
        this.modelProvider = new BlockModelShapes(this);
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        ModelBakery modelbakery = new ModelBakery(resourceManager, this.texMap, this.modelProvider);
		for (ModelResourceLocation loc : ((net.minecraft.util.RegistrySimple<ModelResourceLocation, net.minecraft.client.resources.model.IBakedModel>) modelbakery.bakedRegistry)
				.getKeys()) {
			IBakedModel model = modelbakery.bakedRegistry.getObject(loc);
			try {
				Client.instance.ForamFix(model, 0);
            } catch (Exception e) {

            }
		}
        this.modelRegistry = modelbakery.setupModelRegistry();
        this.defaultModel = (IBakedModel)this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
        this.modelProvider.reloadModels();
    }

    public IBakedModel getModel(ModelResourceLocation modelLocation)
    {
        if (modelLocation == null)
        {
            return this.defaultModel;
        }
        else
        {
            IBakedModel ibakedmodel = (IBakedModel)this.modelRegistry.getObject(modelLocation);
            return ibakedmodel == null ? this.defaultModel : ibakedmodel;
        }
    }

    public IBakedModel getMissingModel()
    {
        return this.defaultModel;
    }

    public TextureMap getTextureMap()
    {
        return this.texMap;
    }

    public BlockModelShapes getBlockModelShapes()
    {
        return this.modelProvider;
    }
}
