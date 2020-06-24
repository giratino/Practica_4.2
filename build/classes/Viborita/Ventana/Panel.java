package Viborita.Ventana;

import Viborita.Utils.Utils;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import Viborita.Utils.Utils;
import java.awt.event.ActionListener;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Viborita.Ventana.VentanaInfo;





public class Panel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JButton playButton = new JButton();
	private JSlider bloquesSlider = new JSlider();
	private JSlider velSlider = new JSlider();
	private JSlider sizeSlider = new JSlider();
	private JLabel scoreLabel = new JLabel();
	private JButton infoButton = new JButton();
	private MainPanel panel;
        private final Clip fondo = Utils.getSound("fondo.wav");
        
                    
	
	public Panel(final MainPanel panel)
	{
                Utils.playSound(fondo);
		this.panel = panel;
		FlowLayout f = new FlowLayout();
		f.setHgap(10);
		this.setLayout(f);
		this.setBorder(new BevelBorder(1));
		
		playButton.setPreferredSize(new Dimension(85, 40));
		playButton.setText("PLAY");
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
                                
                                   
				if(panel.istitulo())
				{
					panel.settitulo(false);
					panel.init();
				}
				else if(panel.isGameOver())
					panel.init();
			}
		});
		
		velSlider.setPreferredSize(new Dimension(60, 40));
		velSlider.setMinimum(1);
		velSlider.setMaximum(10);
		velSlider.setMajorTickSpacing(2);
		velSlider.setToolTipText("Velocidad");
		velSlider.setPaintTicks(true);
		velSlider.setPaintLabels(true);
		velSlider.setValue(panel.getSpeed());
		velSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				panel.setSpeed(velSlider.getValue());
				playButton.requestFocus();				
			}	
		});

		bloquesSlider.setPreferredSize(new Dimension(80, 40));
		bloquesSlider.setMinimum(0);
		bloquesSlider.setMaximum(60);
		bloquesSlider.setMajorTickSpacing(20);
		bloquesSlider.setToolTipText("Bloques");
		bloquesSlider.setPaintTicks(true);
		bloquesSlider.setPaintLabels(true);
		bloquesSlider.setValue(panel.getBlocksNumber());
		bloquesSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				panel.setBlocksNumber(bloquesSlider.getValue());
				playButton.requestFocus();
			}	
		});

		sizeSlider.setPreferredSize(new Dimension(50, 40));
		sizeSlider.setMinimum(1);
		sizeSlider.setMaximum(3);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setToolTipText("Tamaño");
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		
		if(panel.getSquareSize() == 5)
			sizeSlider.setValue(1);
		else if(panel.getSquareSize() == 10)
			sizeSlider.setValue(2);
		else if(panel.getSquareSize() == 20)
			sizeSlider.setValue(3);
		
				
		sizeSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value;
				if(sizeSlider.getValue() == 1)
					value = 5;
				else if(sizeSlider.getValue() == 2)
					value = 10;
				else
					value = 20;
				
				panel.setSquareSize(value);
				playButton.requestFocus();
			}	
		});
		
		scoreLabel.setPreferredSize(new Dimension(70, 40));
		scoreLabel.setText("000");
		scoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
             
                infoButton.setPreferredSize(new Dimension(55, 40));
                infoButton.setText("Com.");
                infoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) 
                        {
			  VentanaInfo vi = new VentanaInfo();
			}
		});
		
		
		
		this.add(playButton);
		this.add(velSlider);
		this.add(bloquesSlider);
		this.add(sizeSlider);
		this.add(scoreLabel);
		this.add(infoButton);
	}
	
	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void disableControls()
	{
		playButton.setEnabled(false);
		bloquesSlider.setEnabled(false);
		velSlider.setEnabled(false);
		sizeSlider.setEnabled(false);
	}
	
	public void enableControls()
	{
		playButton.setEnabled(true);
		bloquesSlider.setEnabled(true);
		velSlider.setEnabled(true);
		sizeSlider.setEnabled(true);
	}
}