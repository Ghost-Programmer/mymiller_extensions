package name.mymiller.javafx.graph;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import name.mymiller.javafx.display.DisplayScreen;
import name.mymiller.javafx.graph.heatmap.ColorMapping;
import name.mymiller.javafx.graph.heatmap.OpacityDistribution;
import name.mymiller.javafx.graph.world.World;
import name.mymiller.javafx.graph.world.World.Resolution;
import name.mymiller.javafx.graph.world.WorldBuilder;

public class WorldHeatMap extends DisplayScreen {

    private static WorldHeatMap global_instance;
    private World worldMap;

    public WorldHeatMap() {
        super("World HeatMap");
    }

    public static WorldHeatMap getInstance() {
        if (WorldHeatMap.global_instance == null) {
            WorldHeatMap.global_instance = new WorldHeatMap();
        }
        return WorldHeatMap.global_instance;
    }

    public void addHeatInfo(Double latitude, Double longitude) {
        final double[] xy = World.latLonToXY(latitude, longitude);
        final Point2D point2d = new Point2D(xy[0], xy[1]);

        this.worldMap.getHeatMap().addEvents(point2d);
    }

    @Override
    protected void startDisplay(Stage stage, double height, double width) throws Exception {
        stage.setTitle("World HeatMap");

        this.worldMap = WorldBuilder.create().resolution(Resolution.HI_RES).zoomEnabled(true).hoverEnabled(false)
                .selectionEnabled(false).colorMapping(ColorMapping.BLUE_YELLOW_RED).fadeColors(true).eventRadius(3)
                .heatMapOpacity(0.75).opacityDistribution(OpacityDistribution.LINEAR).build();

        StackPane pane = new StackPane(this.worldMap);

        final Scene scene = new Scene(pane);

        stage.setTitle("World Cities");
        stage.setScene(scene);
        stage.show();

    }

}
