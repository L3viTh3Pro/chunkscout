package com.chunkscout;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public final class ChunkScoutClient implements ClientModInitializer {
    public static final Settings SETTINGS = new Settings();
    private static KeyMapping menuKey;

    @Override
    public void onInitializeClient() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.chunkscout.menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL,
                KeyMapping.Category.MISC));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.consumeClick()) client.setScreen(new SettingsScreen(client.screen));
        });
    }

    public static final class Settings {
        public boolean storageFinder = true;
        public boolean suspiciousChunks = true;
        public boolean anchors = true;
        public boolean freecam = false;
        public int anchorRadius = 256;
    }

    private static final class SettingsScreen extends Screen {
        private final Screen parent;
        protected SettingsScreen(Screen parent) { super(Component.literal("ChunkScout")); this.parent = parent; }

        @Override protected void init() {
            int x = this.width / 2 - 100;
            int y = this.height / 2 - 90;
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(label("Storage Finder", SETTINGS.storageFinder), b -> { SETTINGS.storageFinder = !SETTINGS.storageFinder; b.setMessage(label("Storage Finder", SETTINGS.storageFinder)); }).bounds(x,y,200,20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(label("Suspicious Chunks", SETTINGS.suspiciousChunks), b -> { SETTINGS.suspiciousChunks = !SETTINGS.suspiciousChunks; b.setMessage(label("Suspicious Chunks", SETTINGS.suspiciousChunks)); }).bounds(x,y+25,200,20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(label("Anchor", SETTINGS.anchors), b -> { SETTINGS.anchors = !SETTINGS.anchors; b.setMessage(label("Anchor", SETTINGS.anchors)); }).bounds(x,y+50,200,20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(label("Freecam", SETTINGS.freecam), b -> { SETTINGS.freecam = !SETTINGS.freecam; b.setMessage(label("Freecam", SETTINGS.freecam)); }).bounds(x,y+75,200,20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal("Anchor radius: " + SETTINGS.anchorRadius), b -> { SETTINGS.anchorRadius += 64; if (SETTINGS.anchorRadius > 1000) SETTINGS.anchorRadius = 0; b.setMessage(Component.literal("Anchor radius: " + SETTINGS.anchorRadius)); }).bounds(x,y+100,200,20).build());
        }
        private static Component label(String name, boolean on) { return Component.literal(name + ": " + (on ? "ON" : "OFF")); }
        @Override public void onClose() { if (this.minecraft != null) this.minecraft.setScreen(parent); }
        @Override public void render(net.minecraft.client.gui.GuiGraphics g, int mouseX, int mouseY, float delta) {
            renderBackground(g, mouseX, mouseY, delta);
            g.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 120, 0xFFFFFF);
            super.render(g, mouseX, mouseY, delta);
        }
    }
}
