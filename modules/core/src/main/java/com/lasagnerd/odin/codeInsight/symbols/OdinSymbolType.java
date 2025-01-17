package com.lasagnerd.odin.codeInsight.symbols;


import lombok.Getter;

@Getter
public enum OdinSymbolType {
    UNKNOWN(false),
    TYPE_ALIAS(true),
    BUILTIN_TYPE(true),
    PARAMETER(false),
    STRUCT_FIELD(false),
    BIT_FIELD_FIELD(false),
    ALLOCATOR_FIELD(false),
    PROCEDURE(true),
    PROCEDURE_OVERLOAD(true),
    STRUCT(true),
    UNION(true),
    ENUM_FIELD(false),
    ENUM(true),
    CONSTANT(false),
    VARIABLE(false),
    PACKAGE_REFERENCE(true),
    POLYMORPHIC_TYPE(true),
    LABEL(false),
    FOREIGN_IMPORT(false),
    SWIZZLE_FIELD(false),
    OBJC_CLASS(true),
    OBJC_MEMBER(true),
    SOA_FIELD(false),
    BIT_FIELD(true),
    BIT_SET(true);

    private final boolean type;

    OdinSymbolType(boolean type) {
        this.type = type;
    }
}
