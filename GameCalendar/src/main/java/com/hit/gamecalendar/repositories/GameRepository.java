package main.java.com.hit.gamecalendar.repositories;

import main.java.com.hit.gamecalendar.Startup;
import main.java.com.hit.gamecalendar.dao.GameModel;
import main.java.com.hit.gamecalendar.repositories.abstracts.BaseRepository;
import main.java.com.hit.gamecalendar.repositories.interfaces.IRepository;

import java.util.List;

public class GameRepository extends BaseRepository implements IRepository<GameModel> {
    public GameRepository() {
        super();
    }

    @Override
    public String tableName() {
        return "games";
    }

    @Override
    public List<GameModel> getAll() {
        return Startup.db.getAllTableItems(tableName(), new GameModel());
    }

    @Override
    public GameModel getItemById(int id) {
        return Startup.db.getTableItemById(tableName(), id, new GameModel());
    }

    @Override
    public Long createTableItem(GameModel m) {
        return Startup.db.createTableItem(tableName(), m);
    }

    @Override
    public boolean updateTableItem(int id, GameModel m) {
        return Startup.db.updateTableItem(tableName(), id, m);
    }

    @Override
    public boolean deleteTableItem(int id) {
        return Startup.db.deleteFromTable(tableName(), id);
    }
}
