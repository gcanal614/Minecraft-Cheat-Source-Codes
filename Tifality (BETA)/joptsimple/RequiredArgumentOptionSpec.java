/*
 * Decompiled with CFR 0.152.
 */
package joptsimple;

import java.util.Collection;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionMissingRequiredArgumentException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class RequiredArgumentOptionSpec<V>
extends ArgumentAcceptingOptionSpec<V> {
    RequiredArgumentOptionSpec(String option) {
        super(option, true);
    }

    RequiredArgumentOptionSpec(Collection<String> options, String description2) {
        super(options, true, description2);
    }

    @Override
    protected void detectOptionArgument(OptionParser parser, ArgumentList arguments2, OptionSet detectedOptions) {
        if (!arguments2.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this.options());
        }
        this.addArguments(detectedOptions, arguments2.next());
    }
}

