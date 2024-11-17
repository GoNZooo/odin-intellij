package com.lasagnerd.odin.codeInsight.typeSystem;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class TsOdinConstrainedType extends TsOdinTypeBase {

    private TsOdinType mainType;
    private TsOdinType specializedType;

    @Override
    public TsOdinMetaType.MetaType getMetaType() {
        return TsOdinMetaType.MetaType.UNKNOWN;
    }

    @Override
    public String getLabel() {

        return label(mainType) + "/" + label(specializedType);
    }
}
