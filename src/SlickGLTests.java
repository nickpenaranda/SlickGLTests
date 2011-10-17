import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SlickGLTests extends BasicGame {
  private int lBeginDraw;
  private int VBOpoints;
  private Texture tTexture;
  private int rX, rY;
  private float rotX = 0, rotY = 0;
  private FloatBuffer ambient, diffuse, position;
  private int VBOindices;
  private int VBOtex;
  private int VBOnorms;
  public SlickGLTests() {
    super("SlickGLTests");

  }

  @Override
  public void init(GameContainer container) throws SlickException {
    rX = rY = 0;
    rotX = 0f;
    rotY = 0f;
    
    lBeginDraw = glGenLists(1);
    glNewList(lBeginDraw,GL_COMPILE);
      glEnable(GL_TEXTURE_2D); 
      glEnable(GL_DEPTH_TEST);
      glEnable(GL_CULL_FACE);
      glEnable(GL_LIGHTING);
      glEnable(GL_LIGHT1);
      glShadeModel(GL_FLAT);
      glDepthFunc(GL_LEQUAL);
      glClearColor(0.6f, 0.6f, 0.5f, 0f);
      glClearDepth(1.0f);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glMatrixMode(GL_PROJECTION);
      glLoadIdentity();
      GLU.gluPerspective(45, 4/3f, 1, 128);
      
      glMatrixMode(GL_MODELVIEW);
      glLoadIdentity();
      GLU.gluLookAt(0f, 0f, -3f, 0f, 0f, 0f, 0f, 1f, 0f);
    glEndList();
    
    ambient = BufferUtils.createFloatBuffer(4);
    ambient.put(new float[] {.2f, .2f, .2f, 1.0f}).flip();

    diffuse = BufferUtils.createFloatBuffer(4);
    diffuse.put(new float[] {1.0f, 1.0f, 1.0f, 1.0f}).flip();

    position = BufferUtils.createFloatBuffer(4);
    position.put(new float[] {0f, 1f, -2f, 1.0f}).flip();
    
    glLight(GL_LIGHT1, GL_AMBIENT, ambient);
    glLight(GL_LIGHT1, GL_DIFFUSE, diffuse);
    glLight(GL_LIGHT1, GL_POSITION, position);
    
    FloatBuffer tBuf = BufferUtils.createFloatBuffer(48);
    tBuf.put(new float[] {0f,0f});
    tBuf.put(new float[] {0f,1f});
    tBuf.put(new float[] {1f,1f});
    tBuf.put(new float[] {1f,0f});

    tBuf.put(new float[] {0f,0f});
    tBuf.put(new float[] {0f,1f});
    tBuf.put(new float[] {1f,1f});
    tBuf.put(new float[] {1f,0f});
    
    tBuf.put(new float[] {0f,0f});
    tBuf.put(new float[] {0f,1f});
    tBuf.put(new float[] {1f,1f});
    tBuf.put(new float[] {1f,0f});
    
    tBuf.put(new float[] {0f,0f});
    tBuf.put(new float[] {0f,1f});
    tBuf.put(new float[] {1f,1f});
    tBuf.put(new float[] {1f,0f});
    
    tBuf.put(new float[] {0f,0f});
    tBuf.put(new float[] {0f,1f});
    tBuf.put(new float[] {1f,1f});
    tBuf.put(new float[] {1f,0f});
    
    tBuf.put(new float[] {0f,0f});
    tBuf.put(new float[] {0f,1f});
    tBuf.put(new float[] {1f,1f});
    tBuf.put(new float[] {1f,0f});
    
    tBuf.flip();
    
    FloatBuffer fBuf = BufferUtils.createFloatBuffer(24);
    fBuf.put(new float[] {0f,0f,0f});
    fBuf.put(new float[] {0f,1f,0f});
    fBuf.put(new float[] {1f,1f,0f});
    fBuf.put(new float[] {1f,0f,0f});
    fBuf.put(new float[] {1f,0f,1f});
    fBuf.put(new float[] {1f,1f,1f});
    fBuf.put(new float[] {0f,1f,1f});
    fBuf.put(new float[] {0f,0f,1f});
    fBuf.flip();
    
    FloatBuffer nBuf = BufferUtils.createFloatBuffer(3 * 24);
    nBuf.put(new float[] {0f,0f,-1f});
    nBuf.put(new float[] {0f,0f,-1f});
    nBuf.put(new float[] {0f,0f,-1f});
    nBuf.put(new float[] {0f,0f,-1f});

    nBuf.put(new float[] {0f,0f,1f});
    nBuf.put(new float[] {0f,0f,1f});
    nBuf.put(new float[] {0f,0f,1f});
    nBuf.put(new float[] {0f,0f,1f});

    nBuf.put(new float[] {0f,-1f,0f});
    nBuf.put(new float[] {0f,-1f,0f});
    nBuf.put(new float[] {0f,-1f,0f});
    nBuf.put(new float[] {0f,-1f,0f});

    nBuf.put(new float[] {0f,1f,0f});
    nBuf.put(new float[] {0f,1f,0f});
    nBuf.put(new float[] {0f,1f,0f});
    nBuf.put(new float[] {0f,1f,0f});

    nBuf.put(new float[] {-1f,0f,0f});
    nBuf.put(new float[] {-1f,0f,0f});
    nBuf.put(new float[] {-1f,0f,0f});
    nBuf.put(new float[] {-1f,0f,0f});

    nBuf.put(new float[] {1f,0f,0f});
    nBuf.put(new float[] {1f,0f,0f});
    nBuf.put(new float[] {1f,0f,0f});
    nBuf.put(new float[] {1f,0f,0f});

    nBuf.flip();
    
    VBOtex = createVBOID();
    glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOtex);
    glBufferDataARB(GL_ARRAY_BUFFER_ARB, tBuf, GL_STATIC_DRAW_ARB);

    VBOnorms = createVBOID();
    glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOnorms);
    glBufferDataARB(GL_ARRAY_BUFFER_ARB, nBuf, GL_STATIC_DRAW_ARB);
    
    VBOpoints = createVBOID();
    glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOpoints);
    glBufferDataARB(GL_ARRAY_BUFFER_ARB, fBuf, GL_STATIC_DRAW_ARB);
    
    IntBuffer buf = BufferUtils.createIntBuffer(24);
    buf.put(0);
    buf.put(1);
    buf.put(2);
    buf.put(3);

    buf.put(4);
    buf.put(5);
    buf.put(6);
    buf.put(7);

    buf.put(7);
    buf.put(0);
    buf.put(3);
    buf.put(4);

    buf.put(5);
    buf.put(2);
    buf.put(1);
    buf.put(6);

    buf.put(7);
    buf.put(6);
    buf.put(1);
    buf.put(0);

    buf.put(3);
    buf.put(2);
    buf.put(5);
    buf.put(4);
    
    buf.flip();

    VBOindices = createVBOID();
    glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, VBOindices);
    glBufferDataARB(GL_ELEMENT_ARRAY_BUFFER_ARB, buf, GL_STATIC_DRAW_ARB);
    
    try {
      tTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("tex.png"), GL_NEAREST);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
  public static int createVBOID() {
    if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
      return glGenBuffersARB();
    }
    return 0;
  }
  
  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    rotX += rX * .25f * delta;
    rotY += rY * .25f * delta;
  }

  @Override
  public void render(GameContainer container, Graphics g) throws SlickException {
    SlickCallable.enterSafeBlock();
    

    glCallList(lBeginDraw);
    
    tTexture.bind();
    glLight(GL_LIGHT1, GL_POSITION, position);
 
    glPushMatrix();
      glRotatef(rotX, 1f, 0f, 0f);
      glRotatef(rotY, 0f, 1f, 0f);
  
      glTranslatef(-.5f, -.5f, -.5f);
      
      position = BufferUtils.createFloatBuffer(4);
      position.put(new float[] {0f, 1f, -2f, 1.0f}).flip();

//      glEnableClientState(GL_VERTEX_ARRAY);
//      glEnableClientState(GL_TEXTURE_COORD_ARRAY);
//      glEnableClientState(GL_NORMAL_ARRAY);
//      
//      glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOnorms);
//      glNormalPointer(GL_FLOAT, 0, 0);
//      
//      glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOtex);
//      glTexCoordPointer(2, GL_FLOAT, 0, 0);
//
//      glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOpoints);
//      glVertexPointer(3, GL_FLOAT, 0, 0);
//
//      glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, VBOindices);
//      glDrawElements(GL_QUADS, 24, GL_UNSIGNED_INT, 0);
//      
//      glDisableClientState(GL_VERTEX_ARRAY);
//      glDisableClientState(GL_TEXTURE_COORD_ARRAY);
//      glDisableClientState(GL_NORMAL_ARRAY);
//
//      glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
//      glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, 0);
      
      glBegin(GL_QUADS);
        glNormal3f(0f, 0f, -1f);
        glTexCoord2f(0f,0f); glVertex3f(0f, 0f, 0f); // front face
        glTexCoord2f(0f,1f); glVertex3f(0f, 1f, 0f);
        glTexCoord2f(1f,1f); glVertex3f(1f, 1f, 0f);
        glTexCoord2f(1f,0f); glVertex3f(1f, 0f, 0f);
        
        glNormal3f(0f, 0f, 1f);
        glTexCoord2f(1f,0f); glVertex3f(1f, 0f, 1f); // back face
        glTexCoord2f(1f,1f); glVertex3f(1f, 1f, 1f); 
        glTexCoord2f(0f,1f); glVertex3f(0f, 1f, 1f);
        glTexCoord2f(0f,0f); glVertex3f(0f, 0f, 1f);
        
        glNormal3f(1f, 0f, 0f);
        glTexCoord2f(0f,0f); glVertex3f(1f, 0f, 0f); // right face
        glTexCoord2f(0f,1f); glVertex3f(1f, 1f, 0f);
        glTexCoord2f(1f,1f); glVertex3f(1f, 1f, 1f);
        glTexCoord2f(1f,0f); glVertex3f(1f, 0f, 1f);
  
        glNormal3f(-1f, 0f, 0f);
        glTexCoord2f(1f,0f); glVertex3f(0f, 0f, 1f); // left face
        glTexCoord2f(1f,1f); glVertex3f(0f, 1f, 1f);
        glTexCoord2f(0f,1f); glVertex3f(0f, 1f, 0f);
        glTexCoord2f(0f,0f); glVertex3f(0f, 0f, 0f); 
  
        glNormal3f(0f, -1f, 0f);
        glTexCoord2f(0f,0f); glVertex3f(0f, 0f, 0f); // bottom face
        glTexCoord2f(0f,1f); glVertex3f(1f, 0f, 0f);
        glTexCoord2f(1f,1f); glVertex3f(1f, 0f, 1f);
        glTexCoord2f(1f,0f); glVertex3f(0f, 0f, 1f);
  
        glNormal3f(0f, 1f, 0f);
        glTexCoord2f(1f,0f); glVertex3f(0f, 1f, 1f); // top face
        glTexCoord2f(1f,1f); glVertex3f(1f, 1f, 1f);
        glTexCoord2f(0f,1f); glVertex3f(1f, 1f, 0f);
        glTexCoord2f(0f,0f); glVertex3f(0f, 1f, 0f); 
      glEnd();
    glPopMatrix();
    
    glFlush();
     
    SlickCallable.leaveSafeBlock();
    
    g.drawString("" + rotX + ", " + rotY, 15, 30);
  }

  @Override
  public void keyPressed(int key, char c) {
    switch (key) {
      case Input.KEY_ESCAPE:
        System.exit(0);
        break;
      case Input.KEY_A:
        rY = -1;
        break;
      case Input.KEY_D:
        rY = 1;
        break;
      case Input.KEY_S:
        rX = -1;
        break;
      case Input.KEY_W:
        rX = 1;
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(int key, char c) {
    switch (key) {
      case Input.KEY_A:
      case Input.KEY_D:
        rY = 0;
        break;
      case Input.KEY_W:
      case Input.KEY_S:
        rX = 0;
        break;
      default:
        break;
    }
  }

  public static void main(String[] args) {
    try {
      AppGameContainer appGameContainer = new AppGameContainer(new SlickGLTests());
      appGameContainer.setDisplayMode(640, 480, false);
      appGameContainer.setMinimumLogicUpdateInterval(16);
      appGameContainer.setTargetFrameRate(120);
      appGameContainer.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }

  }
}
