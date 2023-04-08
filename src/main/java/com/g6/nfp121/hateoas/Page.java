package com.g6.nfp121.hateoas;

import org.springframework.data.domain.Slice;

public interface Page<T> extends Slice<T> {
    int getTotalPages();

    long getTotalElements();
}
