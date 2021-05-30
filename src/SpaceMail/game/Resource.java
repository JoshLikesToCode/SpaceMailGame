package SpaceMail.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> image_resources;

    static {
        Resource.image_resources = new HashMap<>();
        try {
            // example below:
            Resource.image_resources.put("space-bg", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp"))));
            Resource.image_resources.put("moon", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Moon.gif"))));
            Resource.image_resources.put("moons", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Moon.png"))));
            Resource.image_resources.put("ship", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("space_ship.png"))));
            Resource.image_resources.put("barrier", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("blocks.png"))));
            Resource.image_resources.put("meteor", read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Asteroid.gif"))));

        } catch (IOException e) {
            e.printStackTrace();
            // if anything breaks, we want to leave right away
            System.exit(-5);
        }
    }

        public static BufferedImage getResourceImage(String key)
        {
            return Resource.image_resources.get(key);
        }

}

