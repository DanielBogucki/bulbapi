import com.dbogucki.bulbapi.commands.YeelightCommand;
import com.dbogucki.bulbapi.devices.Bulb;
import com.dbogucki.bulbapi.devices.YeelightBulb;
import com.dbogucki.bulbapi.discovering.YeelightDiscoverer;
import com.dbogucki.bulbapi.enums.Category;
import com.dbogucki.bulbapi.exceptions.DeviceNotRecognizedException;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.ResultException;
import com.dbogucki.bulbapi.factories.BulbFactory;

import java.io.IOException;

import java.util.Set;

public class Main {

    public static String lastValue;

    public static void main(String[] args) throws DeviceNotRecognizedException, DeviceSocketException, ResultException, IOException {
        if (args.length >= 1) {
            switch (args[0]) {
                case "help":
                    System.out.println("Current list of commands: ");
                    System.out.println("help - listing commands ");
                    System.out.println("discover - finds bulbs in local network ");
                    System.out.println("toggle [ip port type] - power on/of bulb ");
                    break;
                case "discover":
                    YeelightDiscoverer discoverer = new YeelightDiscoverer();
                    System.out.println("Discovering devices");

                    Set<Bulb> set = discoverer.search();
                    System.out.println("Devices found: " + set.size());
                    for (Bulb b : set) {
                        System.out.println("---------");
                        System.out.println("Address: " + b.getIp() + ":" + b.getPort());
                        System.out.println("Power: " + (b.getPower() ? "On" : "Off"));
                        System.out.println("ColorMode: " + b.getColorMode());
                        System.out.println("Brightness: " + b.getBright() + "%");
                        System.out.println("White temperature: " + b.getColorTemperature() + "K");
                        System.out.println("RGB value: " + b.getRGBvalue());
                        System.out.println("HUE value: " + b.getHUEvalue());
                        System.out.println();
                        b.setPower(true);
                    }
                    break;
                case "toggle":
                    if (args.length >= 4) {
                        Category category = Category.YEELIGHT;
                        switch (args[3]) {
                            case "Yeelight":
                                category = Category.YEELIGHT;
                                break;
                            case "Default":
                                /* falls through */
                            default:
                                category = Category.DEFAULT;
                        }
                        Bulb bulb = BulbFactory.getBulb(category, args[1], Integer.parseInt(args[2]));
                        bulb.toggle();
                    } else
                        System.out.println("Bad parameters. Should be {ip port type}");

                    break;
                default:
                    System.out.println("Unknown Command, please type \"help\"");
            }
        } else {
            System.out.println("This is common API to communicate with Yeelight bulbs");
            System.out.println("type help to get list of supported commands in standalone version");
            System.out.println("Author: Daniel Bogucki");
        }
    }

}


