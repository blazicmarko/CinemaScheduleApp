package com.example.cinema.validator.sequences;

import com.example.cinema.validator.groups.FirstPriorGroup;
import com.example.cinema.validator.groups.SecondPriorGroup;
import com.example.cinema.validator.groups.ThirdPriorGroup;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, FirstPriorGroup.class, SecondPriorGroup.class, ThirdPriorGroup.class})
public interface RequestValidationSequence {
}
