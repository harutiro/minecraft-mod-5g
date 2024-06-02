package com.example.examplemod.ait_5g_system;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ResponseTime {

    private static MinecraftServer serverInstance;
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // サーバーインスタンスを保持する
        serverInstance = event.getServer();
        LOGGER.info("Server starting: {}", serverInstance);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                int ping = Objects.requireNonNull(player.connection.getPlayerInfo(player.getUUID())).getLatency(); // プレイヤーのPingを取得
                LOGGER.info("Player {} has a ping of {} ms", player.getName().getString(), ping);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && serverInstance != null) {
            for (ServerPlayer player : serverInstance.getPlayerList().getPlayers()) {
                int ping = player.latency; // プレイヤーのPingを取得
                LOGGER.info("Player {} has a ping of {} ms", player.getName().getString(), ping);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            int ping = player.connection.getPlayerInfo(player.getUUID()).getLatency(); // プレイヤーのPingを取得
            String pingText = "Ping: " + ping + " ms";
            int width = minecraft.getWindow().getGuiScaledWidth();
            int height = minecraft.getWindow().getGuiScaledHeight();
            int textWidth = minecraft.font.width(pingText);

            RenderSystem.enableBlend();
            minecraft.font.drawShadow(event.getMatrixStack(), pingText, (width / 2.0f) - (textWidth / 2.0f), 10, 0xFFFFFF);
            RenderSystem.disableBlend();
        }
    }
}
