package com.lasagnerd.odin.codeInsight.typeSystem;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TsOdinPointerType extends TsOdinType {
    private TsOdinType dereferencedType;

    @Override
    public String getLabel() {
        return "^" + (dereferencedType != null ? dereferencedType.getLabel() : "<undefined>");
    }

    @Override
    public TsOdinMetaType.MetaType getMetaType() {
        return TsOdinMetaType.MetaType.POINTER;
    }
}
