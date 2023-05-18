import React, { useState, useEffect } from 'react';
import { Button } from 'primereact/button';
import { Carousel } from 'primereact/carousel';
import { Tag } from 'primereact/tag';
import { ProductService } from './ProductService';

export default function PhotoCardCarousel(props) {

    const [products, setProducts] = useState([]);
    // const responsiveOptions = [
    //     {
    //         breakpoint: '1199px',
    //         numVisible: 1,
    //         numScroll: 1
    //     },
    //     {
    //         breakpoint: '991px',
    //         numVisible: 2,
    //         numScroll: 1
    //     },
    //     {
    //         breakpoint: '767px',
    //         numVisible: 1,
    //         numScroll: 1
    //     }
    // ];

    // const getSeverity = (product) => {
    //     switch (product.inventoryStatus) {
    //         case 'INSTOCK':
    //             return 'success';

    //         case 'LOWSTOCK':
    //             return 'warning';

    //         case 'OUTOFSTOCK':
    //             return 'danger';

    //         default:
    //             return null;
    //     }
    // };

    useEffect(() => {
        console.log(props.photocards);
        setProducts(props.photocards)
        // ProductService.getProductsSmall().then((data) => {
        //     console.log(data);
        //     setProducts(data.slice(0, 9));
        // });
    }, []);

    const productTemplate = (product) => {
        return (
            <div className="border-1 surface-border border-round m-2 text-center py-5 px-3">
                <div className="mb-3">
                    {/* <img src={`https://primefaces.org/cdn/primereact/images/product/${product.image}`} alt={product.name} className="w-6 shadow-2" /> */}
                    <h2>url</h2>
                </div>
                <div>
                    <h2>개수</h2>
                </div>
            </div>
        );
    };

    return (
        <div className="card">
            {/* <Carousel value={products} numVisible={3} numScroll={3} responsiveOptions={responsiveOptions} itemTemplate={productTemplate} /> */}
            <Carousel value={products} numVisible={3} numScroll={3} itemTemplate={productTemplate} />
        </div>
    )
}

