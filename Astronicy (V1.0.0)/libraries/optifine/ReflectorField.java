package libraries.optifine;

import java.lang.reflect.Field;

public class ReflectorField
{
    private IFieldLocator fieldLocator;
    private boolean checked;
    private Field targetField;

    public ReflectorField(ReflectorClass p_i96_1_, String p_i96_2_)
    {
        this((IFieldLocator)(new FieldLocatorName(p_i96_1_, p_i96_2_)));
    }

    public ReflectorField(ReflectorClass p_i97_1_, String p_i97_2_, boolean p_i97_3_)
    {
        this(new FieldLocatorName(p_i97_1_, p_i97_2_), p_i97_3_);
    }

    public ReflectorField(ReflectorClass p_i98_1_, Class p_i98_2_)
    {
        this(p_i98_1_, p_i98_2_, 0);
    }

    public ReflectorField(ReflectorClass p_i99_1_, Class p_i99_2_, int p_i99_3_)
    {
        this((IFieldLocator)(new FieldLocatorType(p_i99_1_, p_i99_2_, p_i99_3_)));
    }

    public ReflectorField(Field p_i100_1_)
    {
        this((IFieldLocator)(new FieldLocatorFixed(p_i100_1_)));
    }

    public ReflectorField(IFieldLocator p_i101_1_)
    {
        this(p_i101_1_, false);
    }

    public ReflectorField(IFieldLocator p_i102_1_, boolean p_i102_2_)
    {
        this.fieldLocator = null;
        this.checked = false;
        this.targetField = null;
        this.fieldLocator = p_i102_1_;

        if (!p_i102_2_)
        {
            this.getTargetField();
        }
    }

    public Field getTargetField()
    {
        if (this.checked)
        {
            return this.targetField;
        }
        else
        {
            this.checked = true;
            this.targetField = this.fieldLocator.getField();

            if (this.targetField != null)
            {
                this.targetField.setAccessible(true);
            }

            return this.targetField;
        }
    }

    public Object getValue()
    {
        return Reflector.getFieldValue((Object)null, this);
    }

    public void setValue(Object p_setValue_1_)
    {
        Reflector.setFieldValue((Object)null, this, p_setValue_1_);
    }

    public void setValue(Object p_setValue_1_, Object p_setValue_2_)
    {
        Reflector.setFieldValue(p_setValue_1_, this, p_setValue_2_);
    }

    public boolean exists()
    {
        return this.getTargetField() != null;
    }
}
