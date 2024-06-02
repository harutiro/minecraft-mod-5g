package com.example.examplemod.ait_5g_system;

import com.example.examplemod.ExampleMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ResponseTime {

    private static MinecraftServer serverInstance;
    private static final Logger LOGGER = LogManager.getLogger();

//    public ResponseTime() {
//        // MODイベントバスに登録
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//        // マインクラフトフォージイベントバスに登録
//        MinecraftForge.EVENT_BUS.register(this);
//    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // 初期セットアップコード
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // サーバーインスタンスを保持する
        serverInstance = event.getServer();
        LOGGER.info("Server starting: {}", serverInstance);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) event.getPlayer();
            int ping = serverPlayer.latency; // プレイヤーのPingを取得
            LOGGER.info("Player {} has a ping of {} ms", serverPlayer.getName().getString(), ping);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.player;
            int ping = player.latency; // プレイヤーのPingを取得
            LOGGER.info("Player {} has a ping of {} ms", player.getName().getString(), ping);
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
}
