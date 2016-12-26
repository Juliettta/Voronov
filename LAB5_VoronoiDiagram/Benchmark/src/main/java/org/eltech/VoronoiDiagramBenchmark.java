package org.eltech;

import org.eltech.algorithm.Edge;
import org.eltech.algorithm.Point2D;
import org.eltech.algorithm.VoronoiDiagram;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Fork(1)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class VoronoiDiagramBenchmark {

    @Param({"1", "2", "3", "4"})
    public int size;

    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9"})
    public int subSize;

    List<Point2D> input;

    @Setup
    public void setUp() {
        int length = (int) Math.pow(10, size);
        length *= subSize;
        System.out.println(length);
        if (input == null || input.size() != length) {

            input = new ArrayList<>(length);
            for (int i = 0; i < length; ++i) {
                input.add(new Point2D(Math.random(), Math.random()));
            }
        }

    }

    @Benchmark
    public void testVoronoiDiagramConstruction(Blackhole bh) {
        List<Edge> edges = VoronoiDiagram.createDiagram(input);
        bh.consume(edges);
    }
}
