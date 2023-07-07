package com.dylanensor.sleepysnacks.config;

import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;

public final class IdentifierSerializer implements TypeSerializer<ResourceLocation> {
    public static final IdentifierSerializer INSTANCE = new IdentifierSerializer();

    @Override
    public ResourceLocation deserialize(final @NotNull Type type, final @NotNull ConfigurationNode value) throws SerializationException {
        return fromNode(value);
    }

    @Override
    public void serialize(final @NotNull Type type, final @Nullable ResourceLocation obj, final @NotNull ConfigurationNode value) {
        toNode(obj, value);
    }

    static ResourceLocation fromNode(final ConfigurationNode node) throws SerializationException {
        if (node.virtual()) {
            return null;
        }

        if (node.isList()) {
            final List<? extends ConfigurationNode> children = node.childrenList();
            switch (children.size()) {
                case 2:
                    final String key = children.get(0).getString();
                    final String value = children.get(1).getString();
                    if (key != null && value != null) {
                        return createIdentifier(key, value);
                    }
                    throw listAcceptedFormats();
                case 1:
                    final String combined = children.get(0).getString();
                    if (combined != null) {
                        return createIdentifier(combined);
                    }
                    throw listAcceptedFormats();
                default:
                    throw listAcceptedFormats();
            }
        } else {
            final String val = node.getString();
            if (val == null) {
                throw listAcceptedFormats();
            }
            return new ResourceLocation(val);
        }
    }

    static ResourceLocation createIdentifier(final String key, final String value) throws SerializationException {
        try {
            return new ResourceLocation(key, value);
        } catch (final ResourceLocationException ex) {
            throw new SerializationException(ex);
        }
    }

    static ResourceLocation createIdentifier(final String data) throws SerializationException {
        try {
            return new ResourceLocation(data);
        } catch (final ResourceLocationException ex) {
            throw new SerializationException(ex.getMessage());
        }
    }

    private static SerializationException listAcceptedFormats() {
        return new SerializationException("The provided item must be in [<namespace>:]<path> format");
    }

    static void toNode(final ResourceLocation ident, final ConfigurationNode node) {
        if (ident == null) {
            node.raw(null);
        } else {
            node.raw(ident.toString());
        }
    }










}
