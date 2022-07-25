import com.bsteele.bsteeleMusicApp.JsonDiff;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonDiffTest {

    @Test
    public void testDiff() throws Exception {

        assertNull(JsonDiff.diff(null, null));
        assertNull(JsonDiff.diff("{}", null));
        assertNull(JsonDiff.diff("{ \"state\": \"some\" }", null));
        assertEquals("{ \"state\": foo }", JsonDiff.diff(null, "{ \"state\": foo }") );
        assertEquals("{ \"state\": \"some\" }", JsonDiff.diff("asdfsdf", "{ \"state\": \"some\" }") );
        assertEquals("{ \"state\": foo }", JsonDiff.diff("{ \"state\": \"none\" }", "{ \"state\": foo }") );
        assertEquals("{\n\"state\": \"some\"\n}", JsonDiff.diff("{ \"state\": \"none\" }", "{ \"state\": \"some\" }") );

        String json1 =
"""
{
"state": "none",
"currentKey": "C",
"song": {
"title": "25 or 6 to 4",
"artist": "Chicago",
"user": "Shari",
"lastModifiedDate": 1656830709475,
"copyright": "1969 Columbia, written by Robert Lamm",
"key": "C",
"defaultBpm": 147,
"timeSignature": "4/4",
"chords":
    [
    "I:",
    "Am C/G F#m7b5 FE x4",
    "I2:",
    "Am C/G F#m7b5 FE x2",
    "V:",
    "Am C/G F#m7b5 FE x4",
    "C:",
    "F F C C",
    "G G F F",
    "O:",
    "Am G Gmaj7/B Bb",
    "Asus2"
    ],
"lyrics":
    [
    "i:",
    "(instrumental)",
    "",
    "v:",
    "Waiting for the break of day",
    "Searching for something to say",
    "Flashing lights against the sky",
    "Giving up I close my eyes",
    "",
    "c:",
    "Sitting cross-legged on the floor",
    "25 or 6 to 4",
    "",
    "i2: ",
    "(instrumental)",
    "",
    "v:",
    "Staring blindly into space",
    "Getting up to splash my face",
    "Wanting just to stay awake",
    "Wondering how much I can take",
    "",
    "c:",
    "Should I try to do some more",
    "25 or 6 to 4",
    "",
    "i: (instrumental solo)",
    "",
    "i: (instrumental solo)",
    "",
    "i: (instrumental solo + sax)",
    "",
    "v:",
    "Feeling like I ought to sleep",
    "Spinning room is sinking deep",
    "Searching for something to say",
    "Waiting for the break of day",
    "",
    "ch:",
    "25 or 6 to 4",
    "25 or 6 to 4",
    "",
    "i2:",
    "(instrumental)",
    "",
    "o:"
    ]
}
,
"momentNumber": -1,
"beat": 0,
"user": "bob",
"beatsPerMeasure": 4,
"currentBeatsPerMinute": 147
}
""";


        String json2 = """
{
"state": "none",
"currentKey": "C#",
"song": {
"title": "25 or 6 to 4",
"artist": "Chicago",
"user": "Shari",
"lastModifiedDate": 1656830709475,
"copyright": "1969 Columbia, written by Robert Lamm",
"key": "C",
"defaultBpm": 147,
"timeSignature": "4/4",
"chords":
    [
    "I:",
    "Am C/G F#m7b5 FE x4",
    "I2:",
    "Am C/G F#m7b5 FE x2",
    "V:",
    "Am C/G F#m7b5 FE x4",
    "C:",
    "F F C C",
    "G G F F",
    "O:",
    "Am G Gmaj7/B Bb",
    "Asus2"
    ],
"lyrics":
    [
    "i:",
    "(instrumental)",
    "",
    "v:",
    "Waiting for the break of day",
    "Searching for something to say",
    "Flashing lights against the sky",
    "Giving up I close my eyes",
    "",
    "c:",
    "Sitting cross-legged on the floor",
    "25 or 6 to 4",
    "",
    "i2: ",
    "(instrumental)",
    "",
    "v:",
    "Staring blindly into space",
    "Getting up to splash my face",
    "Wanting just to stay awake",
    "Wondering how much I can take",
    "",
    "c:",
    "Should I try to do some more",
    "25 or 6 to 4",
    "",
    "i: (instrumental solo)",
    "",
    "i: (instrumental solo)",
    "",
    "i: (instrumental solo + sax)",
    "",
    "v:",
    "Feeling like I ought to sleep",
    "Spinning room is sinking deep",
    "Searching for something to say",
    "Waiting for the break of day",
    "",
    "ch:",
    "25 or 6 to 4",
    "25 or 6 to 4",
    "",
    "i2:",
    "(instrumental)",
    "",
    "o:"
    ]
}
,
"momentNumber": 16,
"beat": 0,
"user": "bob",
"beatsPerMeasure": 4,
"currentBeatsPerMinute": 147
}""";

        assertEquals(JsonDiff.diff(json1, json2),"{\n" +
                "\"currentKey\": \"C#\",\n" +
                "\"momentNumber\": 16\n" +
                "}");
    }
}