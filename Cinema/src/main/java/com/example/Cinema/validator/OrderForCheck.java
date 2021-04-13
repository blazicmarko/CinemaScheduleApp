package com.example.Cinema.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, FirstPriorInfo.class, SecondPriorInfo.class, ThirdPriorInfo.class})
public interface OrderForCheck {
}