import './HeroSection.css'
import heroImg from '../../../../assets/images/3.jpg'
import messaging from '../../../../assets/images/messaging5.jpg'
import { Fade } from 'react-reveal'
import {BsFillArrowRightCircleFill} from "react-icons/bs"
const HeroSection = () => {
  return (
    <div className='content'>
      {/* <div className='hero-img-section'>
             <img className='hero-img' src={heroImg}/>
        </div> */}

      <div className='hero-content'>

        <div className='content-left animated fadeIn'>

          {/* <p className='h1 content-header '></p> */}
          <h2 className='hero-text-title'>SMS Gateway<br/><br/>Your Customer Engagement Platform</h2>
          
              

          <span className='hero-text'>
            With simple API intergration and cost-effective solutions,<br/> we connect you to your customers
          </span>

          <button className='hero-button animated bounce w-50 font-weight-bold'>Sign Up and Start Messaging <BsFillArrowRightCircleFill/></button>

     
          {/* <span className='hero-text'>
            With simple API intergration and cost-effective solutions,<br/> we connect you to your customers
          </span> */}
        </div>
        <div className='right-content d-flex flex-column gap-2'>
        <Fade right>
          {/* <img classname='content-img' width='200' height='150' src={messaging}/> */}
          <img className='content-right-img w-100' src={messaging} />
          <div className='content-right-text'> 
         
             <span className='h4 description'>Stay Connected</span>
        
           
          </div>
          </Fade>
        </div>

      </div>
    </div>
  )
}

export default HeroSection