package com.avtdr.vehicletracks.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class PageParam {
    public static PageRequest of(int from, int size) {
        return PageRequest.of(from > 0 ? from : 0, size);
    }
}