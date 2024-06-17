package main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private Game game;

    @Override
    public void init() throws ServletException {
        game = new Game();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        game.update();
        String gameStateJson = game.render();
        resp.setContentType("application/json");
        resp.getWriter().write(gameStateJson);
    }
}
