package com.xyrisdev.blossom.util.adapter;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

	@Override
	public JsonElement serialize(Location loc, Type type, JsonSerializationContext context) {
		if (loc == null) {
			return JsonNull.INSTANCE;
		}

		final JsonObject json = new JsonObject();
		json.addProperty("world", loc.getWorld() != null ? loc.getWorld().getName() : "world");
		json.addProperty("x", loc.getX());
		json.addProperty("y", loc.getY());
		json.addProperty("z", loc.getZ());
		json.addProperty("yaw", loc.getYaw());
		json.addProperty("pitch", loc.getPitch());
		return json;
	}

	@Override
	public @Nullable Location deserialize(@NotNull JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonNull()) {
			return null;
		}

		final JsonObject obj = json.getAsJsonObject();
		World world = Bukkit.getWorld(obj.get("world").getAsString());
		double x = obj.get("x").getAsDouble();
		double y = obj.get("y").getAsDouble();
		double z = obj.get("z").getAsDouble();
		float yaw = obj.get("yaw").getAsFloat();
		float pitch = obj.get("pitch").getAsFloat();
		return new Location(world, x, y, z, yaw, pitch);
	}
}
