import React from 'react'
import { AnimationOnScroll } from 'react-animation-on-scroll'
import './IntroSection.css'
import computers from  '../../../../assets/images/sw5.jpg'
import payment from '../../../../assets/images/payments5.jpg'
import messaging from '../../../../assets/images/messaging5.jpg'
import ussd from '../../../../assets/images/ussd5.jpg'
import { ArrowRight, ArrowRightSharp } from '@mui/icons-material'
import { Fade } from 'react-reveal'

const IntroSection = () => {

    // const years_of_experience = new Date().getFullYear - 2014

    var date = new Date().getFullYear() - 2014
    
    return (
        <div className='content'>

            {/* Intouchsms introduction*/}

            <Fade cascade bottom>

            <div className='row-section gap-1'>
                <div className='col-left'>
                    <AnimationOnScroll animateIn='animate__bounceIn'>
                        <div className='about-left text-center rounded'>
                            <h3 className='h1 font-weight-bold'>{date}+</h3>
                            <p className='h4'>Years <br />of Creativity +Innovation</p>
                        </div>
                    </AnimationOnScroll>
                </div>

                <div className='col-right'>
                    <div className='about-right'>

                        <div className='about-right-title'>
                            <h2 className='h1 '>Unified SMS Communication</h2>
                            <h4 className='h2 '>Since 2014</h4>
                        </div>

                        <div className='about-right-text'>

                        <p className='description'>Intouch Communications SMS Gateway enables you to manage your bulk messaging needs in a simple, cost effective way.
                            Empowering organisations in the business and public sectors to communicate better with their customers and colleagues, through our simple and intuitive online platform, and via our high-performance APIs.
                            <ul className='description'>
                                <li className='description'>Programmatically send high volumes of SMS to Rwandan networks.</li>
                                <li className='description'>Send SMS with low latency and high delivery rates.</li>
                                <li className='description'>Automate your messaging needs through our SMS API.</li>
                                <li className='description'>Receive messages using keywords.</li>
                                <li className='description'>Enable two-way messaging using your shortcode.</li>
                                <li className='description'>Manage your messaging with our easy to use web portal.</li>
                                <li className='description'>View and export usage reports in EXCEL and PDF.</li>
                                <li className='description'>Pay only for what you use, nothing more.</li>
                                <li className='description'>Application to Person (A2P) HTTP API and SMPP connections.</li>
                            </ul>
                        </p>

                        </div>

                    </div>
                </div>

            </div>
            {/* End of Intouchsms introduction */}
            <hr color="grey"/>


            {/* Capabilities of Intouchsms */}

            <div className='about-right-title m-5'>
                <h2 className='h1'>Features, Capabilities, APIs</h2>
                <h4 className='font-weight-bold'>What can the Gateway Do?</h4>
            </div>
            <div className='row-section'>
                <div className='col-left'>
                    <AnimationOnScroll animateIn='animate__bounceIn'>
                        <div className='about-left text-center'>
                            <h3 className='h2'>HTTP API</h3>
                            <p className='h6 description'>Application to Person (A2P) Messaging</p>
                        </div>
                    </AnimationOnScroll>
                </div>

                <div className='col-right'>
                    <div className='about-right'>

                        <p className='description'>Commonly known as an Enterprise Messaging Solution,<br/> A2P (Application-to-Person) is the process that involves an application that sends messages to a mobile user. Besides, A2P can also be used as a customer service tool, activation and authentication mechanism. Typical uses of A2P include travel updates, banking messages and many other value-add messages.<br/>
                        IntouchSMS A2P gives you a highly customizable, cost-effective, reliable yet fast plug-and-play system using our standard HTTP APIs.</p>
                    
                        <button className='home-buttons'> Read More <ArrowRight/></button>

                    </div>
                </div>

            </div>
            {/* <hr /> */}
            <div className='row-section'>
                <div className='col-left'>
                    <AnimationOnScroll animateIn='animate__bounceIn'>
                        <div className='about-left text-center'>
                            <h3 className='h2'>SMPP Access</h3>
                            <p className='description'>Carrier Grade SMS Messaging</p>
                        </div>
                    </AnimationOnScroll>

                </div>

                <div className='col-right'>
                    <div className='about-right'>

                        <p className='description'>SMPP Interconnection protocol between your system and our SMS Gateway allows you to manage a large volume of MT and MO SMS with Carrier grade performace.</p>
                        <p className='description'> SMPP Features:
                            <ul>
                            <li className='description'>Instant Delivery and Low latency.</li>
                            <li className='description'>High throughput (up to 200 msgs/second).</li>
                            <li className='description'>Usage of Short Code.</li>
                            <li className='description'>Standard Status delivery reports.</li>
                            <li className='description'>High-capacity, fast-flow, rapid routing.</li>
                            <li className='description'> Traffic monitoring and 24/7 assistance.</li>
                            </ul>
                            
                        </p>

                        <button className='home-buttons description'> Read More <ArrowRight/></button>

                    </div>
                </div>

            </div>
            {/* <hr /> */}
            <div className='row-section'>
                <div className='col-left'>
                    <AnimationOnScroll animateIn='animate__bounceIn'>
                        <div className='about-left text-center'>
                            <h3 className='h2'>Web Portal</h3>
                            <p className='description'>All-in-One Dashboard</p>
                        </div>
                    </AnimationOnScroll>

                </div>

                <div className='col-right'>
                    <div className='about-right'>

                        <p className='description'>Have control over your messaging activity and much more from one intuitive and user-friendly interface. </p>
                        <ul>
                            <li className='description'>Manage all your messsaging using one friendly interface</li>
                            <li className='description'>Access your usage statistics with applied fitlers</li>
                            <li className='description'>View delivery statuses of your messages</li>
                            <li className='description'> Manage Contacts and Groups</li>
                            <li className='description'> Schedule Messages</li>
                            <li className='description'> Receive incoming messages</li>
                            <li className='description'> Send Customised Messages</li>
                            <li className='description'>Access reports and export them to Excel or PDF</li>
                        </ul>

                        <button className='home-buttons description'> Read More <ArrowRight/></button>

                    </div>
                </div>

            </div>

        
            <div className='row-section'>
                <div className='col-left'>
                    <AnimationOnScroll animateIn='animate__bounceIn'>
                        <div className='about-left text-center'>
                            <h3 className='h2'>Business Accounts</h3>
                            <p className='description'>Agents + SMS Reseller</p>
                        </div>
                    </AnimationOnScroll>

                </div>

                <div className='col-right'>
                    <div className='about-right'>

                        <p className='description'>Create a Business Account, and join our SMS Reseller program to kickstart your SMS business. Build your SMS Gateway or simply use our Web portal and APIs to serve yoru customers, with minimum set up requirements.</p>
                        <p className='description'>This program allows you to take control of promoting SMS to your customers with the full support of our partnership team plus great pricing and packages that allows you to turn a profit.</p>
                        <p className='description'>With this program you can:
                            <ul>
                            <li className='description'> set your own prices</li>
                            <li className='description'> access marketing support from our partnership team</li>
                            <li className='description'> build your own sms gateway using our SMPP and HTTP APIs</li>
                            <li className='description'> integrate your client's systems using our APIs</li>
                            <li className='description'> access 24/7 technical support.</li>
                            </ul>
                        </p>

                        <button className='home-buttons description'> Read More <ArrowRight/></button>

                    </div>
                </div>

            </div>
            </Fade>
            {/* End of capabilities of Intouchsms  */}

            {/* Contact us Section */}
            <div className='contact-section'>
                <div className='contact-section-left'>
                    <p className='h2 description'>Any general or sales enquiries?</p>
                    <p className='description'>
                        Let us help you bring your business closer to customers.<br />
                        Feel free to contact us for general or sales inquires, or for technical support.
                    </p>
                </div>
                <div className='contact-section-left'><br /><br />
                   <p className='description'> +250 788 304 441<br />
                    +250 780 880 209</p>
                </div>

            </div>

            {/* End of Contact us section  */}

            {/* Products and Services  */}

            <div className='products-section'>
                <div className='products-title'>
                      <h3>Products and Services</h3>
                      <p className='description'>What else do we do?</p>
                </div>
                <div className='products-row'>
                     <img src={messaging} className='products-img'/>
                     <img src={payment} className='products-img'/>
                     <img src={ussd} className='products-img'/>
                     <img src={computers} className='products-img'/>
                </div>

            </div>
        </div>

    )
}

export default IntroSection