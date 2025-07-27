/* Collections #2024 */
package com.favourite.collections.infrastructure.service;

import com.favourite.collections.commons.core.data.SearchParameters;
import com.favourite.collections.commons.useradmin.data.CodeData;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


public interface CodeReadService {
	Page<CodeData> retrieveAllCodes(SearchParameters searchParameters);

	ResponseEntity<CodeData> retrieveOneCode(Long codeId);
}
