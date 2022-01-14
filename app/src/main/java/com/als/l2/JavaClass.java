package com.als.l2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JavaClass {


    private void some(){
        List<String> strings = new ArrayList<>(0);
        List<? extends Object> objects;

        objects = strings;

        Object o = objects.get(0);
        String s = (String) objects.get(0);


    }
}
