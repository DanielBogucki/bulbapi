import com.dbogucki.bulbapi.commands.YeelightCommand;
import com.dbogucki.bulbapi.devices.Bulb;
import com.dbogucki.bulbapi.devices.YeelightBulb;
import com.dbogucki.bulbapi.discovering.YeelightDiscoverer;
import com.dbogucki.bulbapi.enums.Category;
import com.dbogucki.bulbapi.exceptions.DeviceNotRecognizedException;
import com.dbogucki.bulbapi.exceptions.DeviceSocketException;
import com.dbogucki.bulbapi.exceptions.ResultException;
import com.dbogucki.bulbapi.factories.BulbFactory;
import com.dbogucki.bulbapi.results.Result;

import java.io.IOException;

import java.util.Map;
import java.util.Set;

public class Main {

    public static String lastValue;

    public static void main(String[] args) throws DeviceNotRecognizedException, DeviceSocketException, ResultException {
        if (args[0] != null)
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
                    try {
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DeviceSocketException e) {
                        e.printStackTrace();
                    } catch (ResultException e) {
                        e.printStackTrace();
                    }
                    break;
                case "toggle":
                    if (args[1] != null && args[2] != null && args[3] != null) {
                        Bulb bulb = BulbFactory.getBulb(Category.valueOf(args[3]), args[1], Integer.parseInt(args[2]));
                        bulb.toggle();
                    } else
                        System.out.println("Bad parameters. Should be {ip port type}");

                    break;
                default:
                    System.out.println("Unknown Command, please type \"help\"");
            }

    }

}
