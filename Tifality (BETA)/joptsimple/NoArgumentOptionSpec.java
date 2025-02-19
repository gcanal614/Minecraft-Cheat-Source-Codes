/*
 * Decompiled with CFR 0.152.
 */
package joptsimple;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class NoArgumentOptionSpec
extends AbstractOptionSpec<Void> {
    NoArgumentOptionSpec(String option) {
        this(Collections.singletonList(option), "");
    }

    NoArgumentOptionSpec(Collection<String> options, String description2) {
        super(options, description2);
    }

    @Override
    void handleOption(OptionParser parser, ArgumentList arguments2, OptionSet detectedOptions, String detectedArgument) {
        detectedOptions.add(this);
    }

    @Override
    public boolean acceptsArguments() {
        return false;
    }

    @Override
    public boolean requiresArgument() {
        return false;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public String argumentDescription() {
        return "";
    }

    @Override
    public String argumentTypeIndicator() {
        return "";
    }

    @Override
    protected Void convert(String argument) {
        return null;
    }

    public List<Void> defaultValues() {
        return Collections.emptyList();
    }
}

