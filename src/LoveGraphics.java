import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class LoveGraphics {
    public static class RectFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String mode = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            int w = args.arg(4).checkint();
            int h = args.arg(5).checkint();
            if (mode.equals("fill")) {
                LoveCanvas.graphics.fillRect(x, y, w, h);
            } else if (mode.equals("line")) {
                LoveCanvas.graphics.drawRect(x, y, w, h);
            }
            return LuaValue.NIL;
        }
    }

    public static class CircleFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String mode = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            int radius = args.arg(4).checkint();
            if (mode.equals("fill")) {
                LoveCanvas.graphics.fillArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
            } else if (mode.equals("line")) {
                LoveCanvas.graphics.drawArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
            }
            return LuaValue.NIL;
        }
    }

    public static class PrintFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String text = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            LoveCanvas.graphics.drawString(text, x, y, Graphics.TOP | Graphics.HCENTER);
            return LuaValue.NIL;
        }
    }

    public static class PrintfFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            String text = args.arg(1).checkjstring();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            int limit = args.arg(4).optint(1000);
            String align = args.arg(5).optjstring("left").toLowerCase();

            if (!align.equals("left") && !align.equals("right") && !align.equals("center")) {
                align = "left";
            }

            String[] lines = wrapText(text, limit);

            for (int i = 0; i < lines.length; i++) {
                int lineY = y + i * 20;
                int lineX = x;

                if (align.equals("center")) {
                    lineX += limit / 2;
                } else if (align.equals("right")) {
                    lineX += limit;
                }

                LoveCanvas.graphics.drawString(lines[i], lineX, lineY, Graphics.TOP | getAlignFlag(align));
            }

            return LuaValue.NIL;
        }

        private String[] wrapText(String text, int limit) {
            String[] tempLines = new String[50];
            int lineCount = 0;

            char[] chars = text.toCharArray();
            int len = chars.length;

            StringBuffer currentLine = new StringBuffer();
            String currentWord = "";
            int currentWidth = 0;

            for (int i = 0; i <= len; i++) {
                char c = (i < len) ? chars[i] : ' ';
                if (c == ' ') {
                    int wordWidth = currentWord.length() * 10;

                    if (currentWidth + wordWidth > limit && currentLine.length() > 0) {
                        tempLines[lineCount++] = currentLine.toString();
                        currentLine = new StringBuffer(currentWord);
                        currentWidth = wordWidth;
                    } else {
                        if (currentLine.length() > 0) {
                            currentLine.append(" ");
                            currentWidth += 10;
                        }
                        currentLine.append(currentWord);
                        currentWidth += wordWidth;
                    }

                    currentWord = "";
                } else {
                    currentWord += c;
                }
            }

            if (currentLine.length() > 0) {
                tempLines[lineCount++] = currentLine.toString();
            }
            
            String[] result = new String[lineCount];
            System.arraycopy(tempLines, 0, result, 0, lineCount);

            return result;
        }


        private int getAlignFlag(String align) {
            if (align.equals("center")) {
                return Graphics.HCENTER;
            } else if (align.equals("right")) {
                return Graphics.RIGHT;
            } else {
                return Graphics.LEFT;
            }
        }
    }

    public static class LineFunc extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            int x1 = args.arg(1).checkint();
            int y1 = args.arg(2).checkint();
            int x2 = args.arg(3).checkint();
            int y2 = args.arg(4).checkint();
            LoveCanvas.graphics.drawLine(x1, y1, x2, y2);
            return LuaValue.NIL;
        }
    }

    public static class SetColor extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            float r = args.arg(1).checkint()*255;
            float g = args.arg(2).checkint()*255;
            float b = args.arg(3).checkint()*255;
            LoveCanvas.setColor((int)r, (int)g, (int)b);
            return LuaValue.NIL;
        }
    }

    public static class NewImage extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            Image image = null;
            try {
                image = Image.createImage("/game/"+args.arg(1).checkjstring());
            } catch (Exception e) {
                LoveCanvas.currentMidlet.notifyDestroyed();
            }
            LuaValue out = LuaValue.tableOf();
            final Image finalImage = image;
            out.set("_image", new ZeroArgFunction() {
                public LuaValue call() {
                    return LuaValue.userdataOf(finalImage);
                }
            });
            out.set("getWidth", new OneArgFunction() {
                public LuaValue call(LuaValue luaValue) {
                    return LuaValue.valueOf(finalImage.getWidth());
                }
            });
            out.set("getHeight", new OneArgFunction() {
                public LuaValue call(LuaValue luaValue) {
                    return LuaValue.valueOf(finalImage.getHeight());
                }
            });

            return out;
        }
    }

    public static class draw extends VarArgFunction {
        public Varargs invoke(Varargs args) {
            LuaValue tImage = args.arg(1).checktable();
            Image image = (Image) tImage.get("_image").call().checkuserdata();
            int x = args.arg(2).checkint();
            int y = args.arg(3).checkint();
            LoveCanvas.graphics.drawImage(image, x, y, Graphics.TOP | Graphics.HCENTER);
            return LuaValue.NIL;
        }
    }


    public static LuaValue create() {
        LuaValue graphicsTable = LuaValue.tableOf();
        graphicsTable.set("rectangle", new RectFunc());
        graphicsTable.set("setColor", new SetColor());
        graphicsTable.set("circle", new CircleFunc());
        graphicsTable.set("print", new PrintFunc());
        graphicsTable.set("line", new LineFunc());
        graphicsTable.set("newImage", new NewImage());
        graphicsTable.set("draw", new draw());
        graphicsTable.set("printf", new PrintfFunc());
        return graphicsTable;
    }
}