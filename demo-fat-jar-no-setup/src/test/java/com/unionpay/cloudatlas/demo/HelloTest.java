package com.lxt.demo;

import org.junit.Assert;
import org.junit.Test;

public class HelloTest {

    @Test
    public void sayHello() {
        Assert.assertEquals("hello up", HelloUtil.sayHello());
    }

    @Test
    public void showContact() {
        Assert.assertEquals("######## 1\n姓名：Aaron\n电话：90827490\n\n######## 2\n姓名：Amy\n电话：83408927\n\n", HelloUtil.showContact());
    }
}