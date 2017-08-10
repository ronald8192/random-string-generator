package com.ronald8192.randomStringGenerator;

import com.ronald8192.advancedRandom.Range;
import com.ronald8192.advancedRandom.SequenceGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ronald8192 on 09/08/2017.
 */
public class SequenceGeneratorBuilder {

    boolean usePreDefinedRange = true;

//    private char[] customChar;
    private Range rangeCustom;
    private Set<Range> preDefinedGroups = new HashSet<>();
    private SequenceGenerator.Mode randomMethod;

    public SequenceGeneratorBuilder usePreDefinedRange(boolean usePreDefinedRange) {
        this.usePreDefinedRange = usePreDefinedRange;
        return this;
    }

    public SequenceGeneratorBuilder setCustomRange(Range range) {
        this.rangeCustom = range;
//        customChar = chars;
//        rangeCustom = new Range(0, chars.length - 1, 1);
        return this;
    }

    public SequenceGeneratorBuilder clearPreDefinedGroup() {
        this.preDefinedGroups.clear();
        return this;
    }

    public SequenceGeneratorBuilder addPreDefinedGroup(PreDefinedGroup group, int minOccurrence) {
        Range r = new Range(group.range().getLowerRange(), group.range().getUpperRange(), minOccurrence);
        this.preDefinedGroups.add(r);
        return this;
    }

    public SequenceGeneratorBuilder setRandomMethod(SequenceGenerator.Mode mode){
        this.randomMethod = mode;
        return this;
    }

    public SequenceGenerator build() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        sequenceGenerator.setMode(randomMethod);
        if(preDefinedGroups.size() == 0){
            sequenceGenerator.addRange(rangeCustom);
        } else {
            sequenceGenerator.addAllRanges(preDefinedGroups);
        }

        return sequenceGenerator;
    }


}
