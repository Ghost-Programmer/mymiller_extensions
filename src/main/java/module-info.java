module extensions {
    requires javafx.base;
    requires javafx.graphics;
    requires org.kordamp.iconli.core;
    requires jdk.httpserver;
    requires java.logging;
    requires java.desktop;
    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires javafx.swing;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;
    requires javafx.web;

    exports name.mymiller.algorithms;
    exports name.mymiller.containers;
    exports name.mymiller.geo;
    exports name.mymiller.httpserver;
    exports name.mymiller.httpserver.filters;
    exports name.mymiller.httpserver.handlers;
    exports name.mymiller.io.message;
    exports name.mymiller.io.net;
    exports name.mymiller.javafx.display;
    exports name.mymiller.javafx.display.editor.code;
    exports name.mymiller.javafx.display.editor.html;
    exports name.mymiller.javafx.graph;
    exports name.mymiller.javafx.graph.world;
    exports name.mymiller.javafx.graph.heatmap;
    exports name.mymiller.javafx.job;
    exports name.mymiller.lang;
    exports name.mymiller.lang.concurrent;
    exports name.mymiller.lang.location;
    exports name.mymiller.lang.reflect;
    exports name.mymiller.lang.singleton;
    exports name.mymiller.lang.validation;
    exports name.mymiller.math;
    exports name.mymiller.models;
    exports name.mymiller.pipelines;
    exports name.mymiller.pipelines.pipes;
    exports name.mymiller.pipelines.switches;
    exports name.mymiller.query;
    exports name.mymiller.task;
    exports name.mymiller.utils;
}
