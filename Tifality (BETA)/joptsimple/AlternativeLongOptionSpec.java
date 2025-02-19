/*
 * Decompiled with CFR 0.152.
 */
package joptsimple;

import java.util.Collections;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.ArgumentList;
import joptsimple.OptionMissingRequiredArgumentException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class AlternativeLongOptionSpec
extends ArgumentAcceptingOptionSpec<String> {
    AlternativeLongOptionSpec() {
        super(Collections.singletonList("W"), true, "Alternative form of long options");
        this.describedAs("opt=value");
    }

    @Override
    protected void detectOptionArgument(OptionParser parser, ArgumentList arguments2, OptionSet detectedOptions) {
        if (!arguments2.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this.options());
        }
        arguments2.treatNextAsLongOption();
    }
}

