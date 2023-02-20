package com.defers.mypastebin.repository;

public class UserQuery {
    public static String findAll() {
        String query = "SELECT \n" +
                "u.username u_username,\n" +
                "u.password u_password,\n" +
                "u.email u_email,\n" +
                "u.created_date u_created_date,\n" +
                "u.updated_date u_updated_date,\n" +
                "r.id r_id,\n" +
                "r.name r_name \n" +
                "FROM public.user u \n" +
                "   LEFT JOIN user_roles ur\n" +
                "   ON u.username = ur.username\n" +
                "       LEFT JOIN role r \n" +
                "       ON ur.role_id = r.id\n";
        return query;
    }

    public static String save() {
        String query = "INSERT INTO public.user (username, password, email) " +
                "values(:username, :password, :email)";
        return query;
    }

    public static String delete() {
        String query = "DELETE FROM public.user u WHERE u.username = :username";
        return query;
    }

    public static String findUserByUsername(boolean blockForUpdate) {
        String query = findAll() + " WHERE u.username = :username";
        if (blockForUpdate) {
            query += " FOR UPDATE";
        }
        return query;
    }

    public static String update() {
        String query = "UPDATE public.user " +
                "SET username = :username, " +
                "password = :password, " +
                "email = :email " +
                "WHERE username = :username";
        return query;
    }
}
