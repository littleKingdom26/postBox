package kr.co.postbox.dto.request

import io.swagger.annotations.ApiModelProperty
import kr.co.postbox.dto.common.SearchCommonDTO

data class RequestSearchDTO(
    @ApiModelProperty(value="검색어")
    val searchKeyword: String? = ""
    ):SearchCommonDTO()
