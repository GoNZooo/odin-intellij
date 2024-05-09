package com.lasagnerd.odin.insights.typeSystem;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class TsOdinProcedureType extends TsOdinType {
    List<TsOdinType> returnTypes = new ArrayList<>();
    List<TsOdinParameter> returnParameters = new ArrayList<>();

    @Override
    public String getLabel() {
        String label = "proc";
        label += getName() != null? getName() : "";
        label += "("+ getParametersString(parameters) + ")";
        String returTypesString = returnTypes.stream().map(TsOdinType::getLabel).collect(Collectors.joining(", "));
        if (!returnTypes.isEmpty()) {
            label += " -> ";
        }

        if (returnTypes.size() > 1) {
            label += "(" + returTypesString + ")";
        }

        if(returnTypes.size() == 1) {
            label += returTypesString;
        }
        return label;
    }
}
