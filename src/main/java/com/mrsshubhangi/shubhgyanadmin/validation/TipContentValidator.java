package com.mrsshubhangi.shubhgyanadmin.validation;

import com.google.api.pathtemplate.ValidationException;
import com.mrsshubhangi.shubhgyanadmin.domain.TipContent;
import org.springframework.stereotype.Component;

@Component
public class TipContentValidator {

    public void validate(TipContent tip) {

        if (tip.getTitle().isBlank()) {
            throw new ValidationException(
                    "Title cannot be empty");
        }
    }
}