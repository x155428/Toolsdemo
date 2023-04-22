package src.test;

import java.util.Scanner;

public class nettest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入 IP 地址：");
        String ip = scanner.nextLine();

        System.out.print("请输入子网掩码位数：");
        int maskBits = scanner.nextInt();

        // 计算子网掩码
        int mask = 0xffffffff << (32 - maskBits);
        int[] maskBytes = {mask >>> 24, mask >> 16 & 0xff, mask >> 8 & 0xff, mask & 0xff};

        // 计算子网地址
        int[] networkAddressBytes = new int[4];
        for (int i = 0; i < 4; i++) {
            networkAddressBytes[i] = Integer.parseInt(ip.split("\\.")[i]) & maskBytes[i];
        }
        String networkAddress = String.format("%d.%d.%d.%d", networkAddressBytes[0], networkAddressBytes[1], networkAddressBytes[2], networkAddressBytes[3]);

        // 计算广播地址
        int[] broadcastAddressBytes = new int[4];
        for (int i = 0; i < 4; i++) {
            broadcastAddressBytes[i] = networkAddressBytes[i] + ((i == 3) ? (1 << (8 - maskBits % 8)) - 1 : 0);
        }
        String broadcastAddress = String.format("%d.%d.%d.%d", broadcastAddressBytes[0], broadcastAddressBytes[1], broadcastAddressBytes[2], broadcastAddressBytes[3]);

        System.out.println("子网掩码: " + maskBits);
        System.out.println("子网地址: " + networkAddress + "/" + maskBits);
        System.out.println("广播地址: " + broadcastAddress);
    }
}

