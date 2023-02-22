package com.defers.mypastebin.repository.query;

public class PasteQuery {
    public static String findAll() {
        var query = "SELECT " +
                "p.id p_id, " +
                "p.text_description p_text_description, " +
                "p.username p_username, " +
                "p.created_date p_created_date, " +
                "p.updated_date p_updated_date, " +
                "u.password u_password, " +
                "u.username u_username, " +
                "u.email u_email, " +
                "u.created_date u_created_date, " +
                "u.created_date u_updated_date " +
                "FROM public.paste p " +
                "   LEFT JOIN public.user u" +
                "   ON p.username = u.username";
        return query;
    }

    public static String findById(boolean blockForUpdate) {
        var query = findAll() + " WHERE p.id = :id";
        if (blockForUpdate) {
            query += " FOR UPDATE OF p";
        }
        return query;
    }

    public static String save() {
        var query = "INSERT INTO public.paste (id, text_description, username) " +
                "VALUES (:id, :text_description, :username)";
        return query;
    }

    public static String delete() {
        var query = "DELETE FROM public.paste p WHERE p.id = :id";
        return query;
    }

    public static String update() {
        var query = "UPDATE public.paste " +
                "SET id = :id, " +
                "text_description = :text_description, " +
                "username = :username " +
                "WHERE id = :id";
        return query;
    }
}
