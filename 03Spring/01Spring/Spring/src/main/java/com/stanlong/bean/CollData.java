package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Getter
@Setter
@ToString
public class CollData {

    private String[] arrayData;
    private List<String> listData;
    private Set<String> setData;
    private Map<String, String> mapData;
    private Properties propsData;

}