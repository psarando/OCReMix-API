<?php

namespace OCR\ApiBundle\Model;

/**
 * Base Entity Model.
 *
 * @package OCR\ApiBundle\Model
 * @author psarando
 */
abstract class Entity
{
    /**
     * @var int|string
     */
    public $id;

    /**
     * @var string
     */
    public $name;

    public function __construct(array $info)
    {
        $this->id = intval($info['id']);
        $this->name = $info['name'];
    }

    protected function dateToYear($dateStr)
    {
        if (empty($dateStr)) {
            return $dateStr;
        }

        return (int)(new \DateTime($dateStr))->format("Y");
    }
}
