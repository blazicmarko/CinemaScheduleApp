package com.example.Cinema.validator;

import javax.validation.GroupSequence;

@GroupSequence({FirstPriorInfo.class, SecondPriorInfo.class, ThirdPriorInfo.class})
public interface OrderForCheck {
}
