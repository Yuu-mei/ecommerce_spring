package com.jmorillo.indieStore.SQLConstants;

public class SQLConstants {
	// This should be obfuscated
	public static final String admin_pass = "123";
	public static final String SQL_OBTAIN_VIDEOGAME_LIST = "SELECT v.title, v.description, v.price, v.videogame_id FROM videogame AS v;";
	public static final String SQL_OBTAIN_VIDEOGAME_DETAIL = "SELECT v.videogame_id, v.description, v.price, v.release_date, v.title, v.video_url, GROUP_CONCAT(d.dev_id)  AS dev_ids, GROUP_CONCAT(d.dev_name) AS dev_names, t.name AS tag_name, t.id AS tag_id, t.description AS tag_desc "
			+ "FROM videogame AS v "
			+ "JOIN games_and_developers AS gd ON gd.videogame_id = v.videogame_id "
			+ "JOIN developer AS d ON gd.dev_id = d.dev_id "
			+ "JOIN tag AS t ON t.id = v.tag_id "
			+ "WHERE v.videogame_id = :id";
	public static final String SQL_OBTAIN_CART_VIDEOGAMES = "SELECT v.videogame_id, v.title, v.price, cartp.quantity "
			+ "FROM videogame AS v, cart_product AS cartp "
			+ "WHERE cartp.videogame_videogame_id = v.videogame_id "
			+ "AND cartp.cart_id = :id "
			+ "ORDER BY v.price DESC;";
	public static final String SQL_DELETE_CART_PRODUCTS = "DELETE FROM cart_product WHERE cart_id = :cart_id";
	public static final String SQL_OBTAIN_LATEST_GAMES = "SELECT v.videogame_id, v.title, v.description, v.price, GROUP_CONCAT(d.dev_id) AS dev_ids, GROUP_CONCAT(d.dev_name) AS dev_names\r\n"
			+ "FROM videogame AS v\r\n"
			+ "JOIN games_and_developers AS gd ON gd.videogame_id = v.videogame_id\r\n"
			+ "JOIN developer AS d ON d.dev_id = gd.dev_id\r\n"
			+ "GROUP BY v.videogame_id\r\n"
			+ "ORDER BY v.release_date DESC\r\n"
			+ "LIMIT 3;";
	public static final String SQL_OBTAIN_VIDEOGAMES_BY_DEV = "SELECT v.videogame_id, v.title, d.dev_name "
			+ "FROM videogame AS v "
			+ "JOIN games_and_developers AS gd ON gd.videogame_id = v.videogame_id "
			+ "JOIN developer AS d ON gd.dev_id = d.dev_id "
			+ "WHERE d.dev_id = :dev_id "
			+ "ORDER BY v.release_date DESC;";
	public static final String SQL_OBTAIN_VIDEOGAMES_BY_DEV_PAGINATION = "SELECT v.videogame_id, v.title, d.dev_name\r\n"
			+ "FROM videogame AS v\r\n"
			+ "JOIN games_and_developers AS gd ON gd.videogame_id = v.videogame_id\r\n"
			+ "JOIN developer AS d ON gd.dev_id = d.dev_id\r\n"
			+ "WHERE d.dev_id = :dev_id\r\n"
			+ "ORDER BY v.release_date DESC\r\n"
			+ "LIMIT :limit OFFSET :offset ";
	public static final String SQL_OBTAIN_VIDEOGAMES_BY_TAG = "SELECT v.videogame_id, v.title, t.name AS tag_name "
			+ "FROM videogame AS v "
			+ "JOIN tag AS t ON t.id = v.tag_id "
			+ "WHERE t.id = :tag_id "
			+ "ORDER BY v.release_date DESC;";
	public static final String SQL_OBTAIN_VIDEOGAMES_BY_TAG_PAGINATION = "SELECT v.videogame_id, v.title, t.name AS tag_name "
			+ "FROM videogame AS v "
			+ "JOIN tag AS t ON t.id = v.tag_id "
			+ "WHERE t.id = :tag_id "
			+ "ORDER BY v.release_date DESC\r\n"
			+ "LIMIT :limit OFFSET :offset ";
	public static final String SQL_OBTAIN_SIMILAR_VIDEOGAMES = "SELECT v.videogame_id, v.title "
			+ "FROM videogame AS v "
			+ "JOIN tag AS t ON v.tag_id = :tag_id "
			+ "WHERE t.id = :tag_id AND v.videogame_id != :videogame_id "
			+ "ORDER BY v.release_date DESC;";
	public static final String SQL_OBTAIN_VIDEOGAMES_BY_NAME = "SELECT v.videogame_id, v.title "
			+ "FROM videogame AS v "
			+ "WHERE v.title LIKE :videogame_title "
			+ "ORDER BY v.title;";
	public static final String SQL_OBTAIN_TOTAL_VIDEOGAMES_BY_NAME = "SELECT COUNT(videogame_id) FROM videogame WHERE title LIKE :videogame_title";
	public static final String SQL_OBTAIN_TOTAL_VIDEOGAMES_BY_DEV = "SELECT COUNT(v.videogame_id)\r\n"
			+ "FROM videogame AS v\r\n"
			+ "JOIN games_and_developers AS gd ON gd.videogame_id = v.videogame_id\r\n"
			+ "JOIN developer AS d ON gd.dev_id = d.dev_id\r\n"
			+ "WHERE d.dev_id = :dev_id ";
	public static final String SQL_OBTAIN_TOTAL_VIDEOGAMES_BY_TAG = "SELECT COUNT(v.videogame_id)\r\n"
			+ "FROM videogame AS v\r\n"
			+ "JOIN tag AS t ON t.id = v.tag_id\r\n"
			+ "WHERE t.id = :tag_id ";
}
