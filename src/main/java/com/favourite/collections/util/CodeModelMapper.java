/* Collections #2024 */
package com.favourite.collections.util;

import com.favourite.collections.commons.useradmin.data.CodeData;
import com.favourite.collections.commons.useradmin.data.CodeValueData;
import com.favourite.collections.commons.useradmin.domain.Code;
import com.favourite.collections.commons.useradmin.domain.CodeValue;

public final class CodeModelMapper {
	public Code fromCodeDataToCode(CodeData code) {
		boolean isSystemDefined = code.isSystemDefined();
		int externalUse = code.getExternalUse();

		return Code.builder().externalUse(externalUse).name(code.getName()).systemDefined(isSystemDefined).build();
	}

	public CodeData fromCodeDataToCode(Code code) {
		boolean isSystemDefined = code.isSystemDefined();
		int externalUse = code.getExternalUse();

		return CodeData.builder().externalUse(externalUse).name(code.getName()).systemDefined(isSystemDefined).build();
	}

	public CodeValue fromCodeValueDataToCodeValue(CodeValueData codeValueData) {
		return CodeValue.builder().label(codeValueData.getLabel()).position(codeValueData.getPosition())
				.description(codeValueData.getDescription()).isActive(codeValueData.getIsActive()).build();
	}

	public CodeValueData fromCodeValueToCodeValueData(CodeValue codeValueData) {
		return CodeValueData.builder().label(codeValueData.getLabel()).position(codeValueData.getPosition())
				.description(codeValueData.getDescription()).isActive(codeValueData.isActive()).build();
	}
}
