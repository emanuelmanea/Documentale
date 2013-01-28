package app;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import SK.gnome.morena.MorenaImage;
import SK.gnome.twain.TwainConstants;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;


@Root
public class Scanner   {
	
//	public static void main(String args[]){
//		Scanner scan = new Scanner();
//		scan.deSerialize("scanner.xml");
//		System.out.print(scan);
//		try {
//			FileOutputStream out = new FileOutputStream("test.pdf");
//			out.write(scan.example());
//			out.close();
//		}
//		catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (TwainException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Element
	private boolean adf;

	@Element
	private boolean duplex;

	@Element
	private boolean grayscale;
	
	@Element
	private boolean blackAndWhite;
	
	/*   
    public List<BufferedImage> aquireBufferedImage() throws TwainException, IOException{
        List<BufferedImage> res = new ArrayList<BufferedImage>();
        
        TwainSource source = TwainManager.getDefaultSource();
        source.setVisible(false);
        source.setIndicators(true);
        //source.setXResolution(100);
        //source.setYResolution(100);
        //source.setColorMode();
        //source.setContrast(300);
        //source.setAutoBright(true);
        //source.setTransferCount(2);
        //source.setPixelType(TwainSource.TWPT_RGB);
        source.setFeederEnabled(adf);
        source.setDuplexEnabled(duplex);
        if(grayscale)
        	source.setGrayScaleMode();
        source.setUnits(TwainConstants.TWUN_CENTIMETERS);
        source.setFrame(0,0,20.99, 29.70);
        System.err.println("Selected source is " + source);
        System.err.println("source.getAutomaticBorderDetection() " + source.getAutomaticBorderDetection());
        System.err.println("source.getDuplexEnabled() " + source.getDuplexEnabled ());
        System.err.println("source.getContrast() " + source.getContrast());
        System.err.println("source.getDuplexEnabled() " + source.getDuplexEnabled());
        System.err.println("source.getSupportedSupportedSizes()  " + source.getSupportedSupportedSizes());
        System.err.println("source.getXResolution() " + source.getXResolution());
        System.err.println("source.getYResolution() " + source.getYResolution());
        
        do {
            if (source!=null) {
                MorenaImage morenaImage = new MorenaImage(source);
                System.err.println("Size of acquired image is "+morenaImage.getWidth()+" x "+morenaImage.getHeight()+" x "+morenaImage.getPixelSize());
                Image image = Toolkit.getDefaultToolkit().createImage(morenaImage);
                res.add(getBufferedImageFromImage(image));
                //ImageIO.write(getBufferedImageFromImage(image), "png", new File("unu" + i + ".png"));
            }
        }
        while(source.hasMoreImages());
        
        TwainManager.close();        
        return res;
    }
    */
    
	public List<Image> aquireImage() throws TwainException, IOException{
        List<Image> res = new ArrayList<Image>();
        this.deSerialize("c:\\documentale\\scanner.xml");
        TwainSource source = TwainManager.getDefaultSource();
        source.setVisible(false);
        source.setIndicators(true);
        
        source.setUnits(TwainConstants.TWUN_CENTIMETERS);
        source.setFrame(0,0,20.99, 29.70);
        
        source.setSupportedSizes(TwainConstants.TWSS_A4);
      	        
        source.setFeederEnabled(adf);
        source.setDuplexEnabled(duplex);
        if(grayscale)
        	source.setGrayScaleMode();
        if(blackAndWhite){
        	source.setPixelType(TwainSource.TWPT_BW);
        	source.setResolution(150);
        }
        System.err.println("Selected source is " + source);
        System.err.println("source.getFeederEnabled() " + source.getFeederEnabled());
        System.err.println("source.getDuplexEnabled " + source.getDuplexEnabled());
        System.err.println("source.getAutomaticBorderDetection() " + source.getAutomaticBorderDetection());
        System.err.println("source.getContrast() " + source.getContrast());
        System.err.println("source.getSupportedSupportedSizes()  " + source.getSupportedSupportedSizes());
        System.err.println("source.getXResolution() " + source.getXResolution());
        System.err.println("source.getYResolution() " + source.getYResolution());
        System.err.println("source.getAutomaticRotate() " + source.getAutomaticRotate());
        System.err.println("source.setAutomaticDeskew() " + source.getAutomaticDeskew());
        System.err.println("source.getFlipRotation() " + source.getFlipRotation());
        
        do {
            if (source!=null) {
                MorenaImage morenaImage = new MorenaImage(source);
                System.err.println("Size of acquired image is "+morenaImage.getWidth()+" x "+morenaImage.getHeight()+" x "+morenaImage.getPixelSize());
                Image image = Toolkit.getDefaultToolkit().createImage(morenaImage);
                res.add(image);
                //ImageIO.write(getBufferedImageFromImage(image), "png", new File("unu" + i + ".png"));
            }
        }
        while(source.hasMoreImages());
        
        TwainManager.close();        
        return res;
    }
    

	@SuppressWarnings("unused")
	private BufferedImage getBufferedImageFromImage(Image img) {
        //This line is important, this makes sure that the image is
        //loaded fully
        img = new ImageIcon(img).getImage();
        //Create the BufferedImage object with the width and height of the Image
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //Create the graphics object from the BufferedImage
        Graphics g = bufferedImage.createGraphics();
        //Draw the image on the graphics of the BufferedImage
        g.drawImage(img, 0, 0, null);
        //Dispose the Graphics
        g.dispose();
        //return the BufferedImage
        return bufferedImage;
    } 
    
    public byte[] example(String nomeEnte, String noProtocollo, Date data) throws IOException,TwainException,DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        Scanner scan = new Scanner();
        List<Image> img = new ArrayList<Image>();
        
        img = scan.aquireImage();
        
        Document doc = new Document(PageSize.A4,0,0,0,0);
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        doc.open();
        
        BaseFont bfbold = BaseFont.createFont(BaseFont.COURIER_BOLD,BaseFont.CP1252,false);
        BaseFont bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,false);
        
        int pageCount = 1;
        for(Image i : img){
        	com.itextpdf.text.Image im = com.itextpdf.text.Image.getInstance(i,null);
        	//if duplex rotates  every single B image
        	if(duplex)
        		if(pageCount % 2 == 0)
        			im.setRotationDegrees(180);
        	//trim image to fit on the first page
        	if(pageCount != 1){
        		im.setAlignment(com.itextpdf.text.Image.UNDERLYING);
        		im.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                doc.add(im);
        	}
        	else{
        		im.setAlignment(com.itextpdf.text.Image.UNDERLYING);
        		im.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        		doc.add(im);
        		
        		PdfContentByte over = writer.getDirectContent();
        		over.saveState();
        		over.beginText();
        		
        		String dateFormat = "dd-MMM-yyyy";
          		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                
                float width1 = PageSize.A4.getWidth() - bf.getWidthPoint( sdf.format(data), 16);
                float width2 = PageSize.A4.getWidth() - bf.getWidthPoint( noProtocollo, 16);
                //float height = PageSize.A4.getHeight()-16-PageSize.A4.getBorderWidthRight();
              
                
                over.setFontAndSize(bfbold, 16);
                over.showTextAligned(com.itextpdf.text.Element.ALIGN_CENTER, nomeEnte, PageSize.A4.getWidth()/2, PageSize.A4.getHeight()-16, 0);
                over.setFontAndSize(bf, 16);
                over.showTextAligned(com.itextpdf.text.Element.ALIGN_LEFT, sdf.format(data), width1, PageSize.A4.getHeight()-16, 0);
                over.showTextAligned(com.itextpdf.text.Element.ALIGN_LEFT, noProtocollo, width2, PageSize.A4.getHeight()-16*2, 0);
                
                over.endText();
                over.restoreState();
        	}

            doc.newPage();
            System.out.println(img.indexOf(i));
            pageCount++;
        }
        doc.close();
        return out.toByteArray();
    }       
    
    public byte[] writeOverPdf(String ente, String noPrott, Date data, String fileIn){
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		
    	try{
			PdfReader reader = new PdfReader(fileIn);
			PdfImportedPage page;
			
			Document doc = new Document(PageSize.A4,0,0,0,0);
			PdfWriter writer = PdfWriter.getInstance(doc, out);
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			BaseFont bfbold = BaseFont.createFont(BaseFont.COURIER_BOLD,BaseFont.CP1252,false);
	        BaseFont bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,false);
			
			/*
			System.out.println(reader.getNumberOfPages() + " pages");
			System.out.println(reader.getPageSize(1));
			*/
			
			for(int i=1; i<=reader.getNumberOfPages(); i++){
				page = writer.getImportedPage(reader, i);
				com.itextpdf.text.Image im = com.itextpdf.text.Image.getInstance(page);
				
				im.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
				
				doc.add(im);
				
				// to test
				if(i==1){
					PdfContentByte over = writer.getDirectContent();
	        		over.saveState();
	        		over.beginText();
	        		String dateFormat = "dd-MMM-yyyy";
	          		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	                float width1 = PageSize.A4.getWidth() - bf.getWidthPoint( sdf.format(data), 16);
	                float width2 = PageSize.A4.getWidth() - bf.getWidthPoint( noPrott, 16);
	                over.setFontAndSize(bfbold, 16);
	                over.showTextAligned(com.itextpdf.text.Element.ALIGN_CENTER, ente, PageSize.A4.getWidth()/2, PageSize.A4.getHeight()-16, 0);
	                over.setFontAndSize(bf, 16);
	                over.showTextAligned(com.itextpdf.text.Element.ALIGN_LEFT, sdf.format(data), width1, PageSize.A4.getHeight()-16, 0);
	                over.showTextAligned(com.itextpdf.text.Element.ALIGN_LEFT, noPrott, width2, PageSize.A4.getHeight()-16*2, 0);
	                over.endText();
	                over.restoreState();
				}
			}
			
			doc.close();
			reader.close();
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
    	return out.toByteArray();
	}
	
    @Override
	public String toString() {
		return "adf:" + this.isAdf() + " " + "duplex:" + this.isDuplex() + " " + "grayscale:" + this.isGrayscale();
	}

	public boolean isAdf() {
		return adf;
	}

	public void setAdf(boolean newValue) {
		adf = newValue;
		
	}

	public boolean isDuplex() {
		return duplex;
	}

	public void setDuplex(boolean newValue) {
		duplex = newValue;
		
	}

	public boolean isGrayscale() {
		return grayscale;
	}

	public void setGrayscale(boolean newValue) {
		grayscale = newValue;
	}
	
    public boolean isBlackAndWhite() {
		return blackAndWhite;
	}

	public void setBlackAndWhite(boolean blackAndWhite) {
		this.blackAndWhite = blackAndWhite;
	}
	
	public void serialize(String file){
		try {
			Serializer serializer = new Persister();
			serializer.write(this, new File(file));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deSerialize(String file){
		try {
			Serializer serializer = new Persister();
			Scanner newValue = serializer.read(Scanner.class, new File(file));
			this.adf = newValue.adf;
			this.duplex = newValue.duplex;
			this.grayscale = newValue.grayscale;
			this.blackAndWhite = newValue.blackAndWhite;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

