/* Collections #2024 */
package com.favourite.collections.infrastructure.service;

import com.favourite.collections.commons.core.data.SearchParameters;
import com.favourite.collections.commons.useradmin.data.CodeValueData;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


public interface CodeValueReadService {
	Page<CodeValueData> retrieveAllCodeValues(SearchParameters searchParameters);

	ResponseEntity<CodeValueData> retrieveOneCodeValue(Long codeId);
}
