package main.java.edu.miu.cs.cs525.reversi.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URL;

import main.java.edu.miu.cs.cs525.reversi.common.BoardInfo;
import main.java.edu.miu.cs.cs525.reversi.common.GeneralPlayer;
import main.java.edu.miu.cs.cs525.reversi.common.Location;
import main.java.edu.miu.cs.cs525.reversi.utils.Convert;
import main.java.edu.miu.cs.cs525.reversi.utils.ConvertToInt;

public class NetworkPlayer extends GeneralPlayer {

	public static int counterNetwork;
	static Channel channel = new Channel();
	static String hostAddress;
	int portNumber;
	static int portNumber2;
	static InetSocketAddress address;
	private static TargetJson targetJson = new JsonAdapter();
	Convert convert = new ConvertToInt();

	public NetworkPlayer(String hostAddress, int portNumber, int portNumber2) {
		try {
			channel.bind(portNumber);
			NetworkPlayer.hostAddress = hostAddress;
			this.portNumber = portNumber;
			NetworkPlayer.portNumber2 = portNumber2;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Location getMove(BoardInfo b) {

		try {
			channel.start();
			Location move = new Location();
			address = new InetSocketAddress(hostAddress, portNumber2);
			if (b.getStandardFormGame() != null && !b.getStandardFormGame().isEmpty()) {
				String pos[] = b.getStandardFormGame().split(" ");
				// System.out.println("A "+hostAddress);
				if (hostAddress.startsWith("https://")) {
					String result = channel.postRequest(new URL(hostAddress), "POST", pos[pos.length - 1]);
					System.out.println("B " + result);
					if (targetJson.isJson(result)) {
						System.out.println("AdapteeReceived: " + targetJson.JsontoString(result));
						move.set(targetJson.JsontoString(result));
						return move;
					}

				} else if (hostAddress.startsWith("http://")) {
					char[] positions = pos[pos.length - 1].toCharArray();
					StringBuilder url = new StringBuilder();
					url.append(hostAddress + "?");
					url.append("x=" + String.valueOf(convert.stringToInt(positions[0])));
					url.append("&");
					url.append("y=" + Character.getNumericValue(positions[1] - 1));
					System.out.println("Team 2 URL equals " + url.toString());
					String result = channel.getRequestTeam2(new URL(url.toString()), "GET");
					System.out.println("Team 2 result: " + result);
					if (targetJson.isJson(result)) {
						System.out.println("AdapteeReceived: " + targetJson.JsontoString(result));
						move.set(targetJson.JsontoString(result));
						return move;
					}
				}

				else {
					channel.sendTo(address, pos[pos.length - 1]);
				}

			}
			move.set(channel.receiveFrom());
			return move;
		} catch (IOException e) {
			channel.stop();
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String identify() throws Exception {
		return "We good";
	}

	public static Location getMove1(String boardInfo) {
		try {
			channel.start();
			Location move = new Location();
			address = new InetSocketAddress(hostAddress, portNumber2);



			//    if (b.getStandardFormGame() != null && !b.getStandardFormGame().isEmpty()) {
			//    String pos[] = boardInfo.split(" ");
			// System.out.println("A "+hostAddress);
			if (hostAddress.startsWith("https://")) {
				String result = channel.postRequest(new URL(hostAddress), "POST", boardInfo);
				System.out.println("getMove1 result " + result);
				if (targetJson.isJson(result)) {
					System.out.println("getMove1 AdapteeReceived: " + targetJson.JsontoString(result));
					move.set(targetJson.JsontoString(result));
					return move;
				}



			}
//                else if (hostAddress.startsWith("http://")) {
//                    char[] positions = pos[pos.length - 1].toCharArray();
//                    StringBuilder url = new StringBuilder();
//                    url.append(hostAddress + "?");
//                    url.append("x=" + String.valueOf(utils.charToInt(positions[0])));
//                    url.append("&");
//                    url.append("y=" + Character.getNumericValue(positions[1] - 1));
//                    System.out.println("Team 2 URL equals " + url.toString());
//                    String result = channel.getRequestTeam2(new URL(url.toString()), "GET");
//                    System.out.println("Team 2 result: " + result);
//                    if (targetJson.isJson(result)) {
//                        System.out.println("AdapteeReceived: " + targetJson.JsontoString(result));
//                        move.set(targetJson.JsontoString(result));
//                        return move;
//                    }
//                }



//                else {
//                    channel.sendTo(address, pos[pos.length - 1]);
//                }



//            } else {
//                System.out.println("is it getting called also at the end " + b.getStandardFormGame());
//            }
			move.set(channel.receiveFrom());
			return move;
		} catch (IOException e) {
			// channel.stop();
			e.printStackTrace();
		}
		return null;
	}
	public static void getEndMove() {
		try {
			if (hostAddress != null) {
				if (!hostAddress.startsWith("https://")) {
					channel.start();
					address = new InetSocketAddress(hostAddress, portNumber2);
					channel.sendTo(address, "Game over!!!");
					channel.stop();
					return;
				} else if (hostAddress.startsWith("https://")) {
					String result = channel.postRequest(new URL(hostAddress), "POST", "Game over!!!");
					System.out.println("B1 " + result);
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}


}
