package Project1Disassembler;

//Computer Architecture Project 1
//This program will read eleven hex instructions
//The instructions will be processed and
//The registers & offsets belonging to the instruction
//Will be printed $#.
//Registers run from 0-31 making a total of 32 bits address
//This program also keeps a PC count so that branching is assessed(ben, bne)
//There will be an offset for the immediate 16-address
//Load word (lw), store word (sw) will also be processed to determine
//Its offset value.


public class Project1 {

	public static void main(String[] args) {
		
		
		Project1 me = new Project1();
		me.doIt();
		

	} //end of main
	
	public void doIt(){
		
		// op codes for the functions we will be handling for project1
				int sub = 0x22; 	// 
				int add = 0x20; 	//
				int lw = 0x23;		//
				int sw = 0x2B;		//
				int beq = 0x4;		//
				int bne = 0x5;		//
				int and = 0x24; 	//
				int or = 0x25; 		//
				int pcCounter = 0x7A060;
				
		// variables to extract the register values
				int rSrc1Reg = 0, rSrc2Reg = 0, rSrcDestReg = 0;
				int iSrc1Reg = 0, offset = 0, iSrcDestReg = 0, newAddress = 0;
				short immediateOffset;
				
				
				int [] arrayInstructions = new int [] {0x022DA822, 0x8EF30018, 0x12A70004, 0x02689820, 0xAD930018, 0x02697824, 0xAD8FFFF4, 0x018C6020, 0x02A4A825, 0x158FFFF6, 0x8E59FFF0};
				
				System.out.println("Address\t- Mips Instructions");
				System.out.println("-------------------------");
				
				//////////////////////////////////////////////////////
				// MASKING TO GET VALUES				  			//	
				// opCode (if R format)  0x0000003F  				//	
				// opCode (if I format) iFunct 0xFC000000 >>> 26 	//
				// src1Reg 0x03E00000 >>> 21						//
				// src2Reg 0x001F0000 >>> 16						//
				// srcDestReg 0x0000F800 >>> 11						//
				// immediate 0x0000FFFF >> 15 to check bit on/off	//
				//////////////////////////////////////////////////////
				
				// step through arrayInstructions and read each hex instruction
				
				for (int i = 0; i < arrayInstructions.length; i++) {
					
					// assign each value of the array to variable instruction
					int instructions = arrayInstructions[i];
					
					rSrc1Reg = (instructions & 0x03E00000) >>> 21;
					rSrc2Reg = (instructions & 0x001F0000) >>> 16;
					rSrcDestReg = (instructions & 0x0000F800) >>> 11;
					
					// check if the first 6 bits are zeros
					if ( ((instructions & 0xFC000000) >>> 26) == 0x00 &&
							(instructions & 0x0000003F) == sub) {
			
								
						System.out.println(Integer.toHexString(pcCounter) + " -\tsub $" +
								rSrcDestReg + ", $" +
								rSrc1Reg + ", $" + 
								rSrc2Reg);
						
					}//end of 1st if statement
					
					 if ( ((instructions & 0xFC000000) >>> 26) == 0x00 &&
							(instructions & 0x0000003F) == add) {
						 
						 System.out.println(Integer.toHexString(pcCounter) + " -\tadd $" +
								 rSrcDestReg + ", $" +
									rSrc1Reg + ", $" + 
									rSrc2Reg);					
				
					} //end of second if statement				
					
					 if ( ((instructions & 0xFC000000) >>> 26) == 0x00 &&
								(instructions & 0x0000003F) == and) {
									
						 System.out.println(Integer.toHexString(pcCounter) + " -\tand $" +
								 rSrcDestReg + ", $" +
									rSrc1Reg + ", $" + 
									rSrc2Reg);
					
					} //end of third if statement		
					
					 if ( ((instructions & 0xFC000000) >>> 26) == 0x00 &&
								(instructions & 0x0000003F) == or) {
									
						 System.out.println(Integer.toHexString(pcCounter) + " -\tor  $" +
								 rSrcDestReg + ", $" +
									rSrc1Reg + ", $" + 
									rSrc2Reg);
					
					 } //end of fourth if statement
					
					 
			 // I-format sources
					 iSrc1Reg = (instructions & 0x03E00000 ) >>> 21;
					 iSrcDestReg = (instructions & 0x001F0000 ) >>> 16;
					 offset = (instructions & 0x0000FFFF);
					 immediateOffset = (short) (instructions & 0x0000FFFF);
					 
					 newAddress = (pcCounter+4) + (immediateOffset*4);

					 
					 if ( (instructions & 0xFC000000) >>> 26 == lw  && (instructions & 0x0000FFFF) >> 15 != 1) { 
									
						 System.out.println(Integer.toHexString(pcCounter) + " -\tlw  $" +
									iSrcDestReg + ", " +
									offset + " ($" + 
									iSrc1Reg + ")");
						 
					 }
					 
					// if is negative
					 if ( (instructions & 0xFC000000) >>> 26 == lw  && (instructions & 0x0000FFFF) >> 15 == 1) { 
					 
						 
						 System.out.println(Integer.toHexString(pcCounter) + " -\tlw  $" +
									iSrcDestReg + ", " +
									immediateOffset + " ($" + 
									iSrc1Reg + ")");
						 
					
					 } //end of fifth if statement
					
					 if ( ((instructions & 0xFC000000) >>> 26) == sw  && (instructions & 0x0000FFFF) >> 15 != 1) {
						 
						 System.out.println(Integer.toHexString(pcCounter) + " -\tsw  $" +
									iSrcDestReg + ", " +
									offset + " ($" + 
									iSrc1Reg + ")");
						 
					 }  
						
					 // if is negative
					 if ( (instructions & 0xFC000000) >>> 26 == sw  && (instructions & 0x0000FFFF) >> 15 == 1) { 
						 
						
						 System.out.println(Integer.toHexString(pcCounter) + " -\tsw  $" +
									iSrcDestReg + ", " +
									immediateOffset + " ($" + 
									iSrc1Reg + ")");
						 
					 } //end of sixth "store word - sw" if statement
					  					 
					 if ( ((instructions & 0xFC000000) >>> 26) == beq ) {						 
						 
						 System.out.println(Integer.toHexString(pcCounter) + " -\tbeq $" +
									iSrc1Reg + ", $" +
									iSrcDestReg + ", " + Integer.toHexString(newAddress));
											 
					 } //end of 7th if statement					
					 
					 if ( ((instructions & 0xFC000000) >>> 26) == bne ) {
							
						 System.out.println(Integer.toHexString(pcCounter) + " -\tbne $" +
									iSrc1Reg + ", $" +
									iSrcDestReg + ", " + Integer.toHexString(newAddress));
					
					 } //end of 8th if statement					
										
				pcCounter += 0x4;
			} //end of for loop	
	} // end of doIT()
} // end of class
