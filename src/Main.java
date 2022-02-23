import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;

@ScriptManifest(name="name", gameType = GameType.OS)
public class Main extends LoopScript {

    @Override
    protected int loop() {
        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        return false;
    }
}
