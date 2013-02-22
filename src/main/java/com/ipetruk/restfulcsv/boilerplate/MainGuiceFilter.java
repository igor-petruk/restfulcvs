package com.ipetruk.restfulcsv.boilerplate;

import com.google.inject.servlet.GuiceFilter;

import static javax.servlet.DispatcherType.*;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*", dispatcherTypes = {ASYNC, FORWARD, INCLUDE, REQUEST})
public class MainGuiceFilter extends GuiceFilter {
}
