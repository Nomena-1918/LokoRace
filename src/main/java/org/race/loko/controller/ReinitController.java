package org.race.loko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReinitController {
    public final JdbcTemplate jdbcTemplate;
    @Autowired
    public ReinitController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reinit")
    public String reinit() {
        String dropTableQuery =
        """
               select reset_database();
        """;
        jdbcTemplate.execute(dropTableQuery);
        return "redirect:/";
    }
}
